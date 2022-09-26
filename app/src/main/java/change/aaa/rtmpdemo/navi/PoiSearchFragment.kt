package change.aaa.rtmpdemo.navi

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.os.Message
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.widget.EditText
import android.widget.ImageButton
import android.widget.ProgressBar
import android.widget.TextView
import androidx.annotation.NonNull
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import change.aaa.rtmpdemo.DEBUG_PRE
import change.aaa.rtmpdemo.R
import change.aaa.rtmpdemo.StageActivity
import change.aaa.rtmpdemo.util.invisibleIf
import com.baidu.location.BDAbstractLocationListener
import com.baidu.location.BDLocation
import com.baidu.location.LocationClient
import com.baidu.location.LocationClientOption
import com.baidu.mapapi.SDKInitializer
import com.baidu.mapapi.map.MapStatus
import com.baidu.mapapi.map.MapStatusUpdateFactory
import com.baidu.mapapi.map.MapView
import com.baidu.mapapi.map.MyLocationData
import com.baidu.mapapi.model.LatLng
import com.baidu.mapapi.search.core.SearchResult
import com.baidu.mapapi.search.poi.*
import com.baidu.navisdk.adapter.BNRoutePlanNode
import com.baidu.navisdk.adapter.BNaviCommonParams
import com.baidu.navisdk.adapter.BaiduNaviManagerFactory
import com.baidu.navisdk.adapter.IBNRoutePlanManager
import link.dayang.rtmpdemo.navi.NaviActivity


class PoiSearchFragment : Fragment() {

    private lateinit var root: ViewGroup

    private lateinit var poiSearch: PoiSearch

    private lateinit var mLocationClient: LocationClient
    private val myListener = LocationListener()

    private var curLocation: BDLocation? = null
    private var city = ""
    private var keyword = ""
    private var isSubmitted = false
    private var loadedPage = -1
    private val isFetchingLocation = MutableLiveData(true)

    private val isFetchingPoi = MutableLiveData(false)
    private val poiResData = MutableLiveData(emptyList<PoiSearchListItem>())

    private lateinit var bmap: MapView


    private lateinit var poiSearchInput: EditText
    private lateinit var poiSearchSubmit: ImageButton
    private lateinit var poiSearchRes: RecyclerView
    private lateinit var poiSearchResAdapter: PoiSearchAdapter
    private lateinit var poiSearchFetchingLocationProgress: ProgressBar
    private lateinit var poiSearchFetchingLocationText: TextView
    private lateinit var poiFetchingPoiProgress: ProgressBar

    private lateinit var loadMoreOnScrollListener: PoiLoadMoreOnScrollListener

    private lateinit var loadingDialog: AlertDialog


    private val handler: Handler = object : Handler(Looper.getMainLooper()) {
        override fun handleMessage(@NonNull msg: Message) {
            when (msg.what) {
                IBNRoutePlanManager.MSG_NAVI_ROUTE_PLAN_START -> {
                    toast("算路开始")
                }
                IBNRoutePlanManager.MSG_NAVI_ROUTE_PLAN_SUCCESS -> {
                    loadingDialog.dismiss()
                    toast("算路成功")
                    val intent = Intent(activity, NaviActivity::class.java)
                    startActivity(intent)
                    // 躲避限行消息
                    val infoBundle = msg.obj as Bundle
                    val info = infoBundle
                        .getString(BNaviCommonParams.BNRouteInfoKey.TRAFFIC_LIMIT_INFO)
                    Log.e("OnSdkDemo", "info = $info")
                }
                IBNRoutePlanManager.MSG_NAVI_ROUTE_PLAN_FAILED -> {
                    loadingDialog.dismiss()
                    toast("算路失败")
                }
                IBNRoutePlanManager.MSG_NAVI_ROUTE_PLAN_TO_NAVI -> {
                    loadingDialog.dismiss()
                    toast("算路成功准备进入导航")
                    val intent = Intent(activity, NaviActivity::class.java)
                    startActivity(intent)
                }
                else -> {
                }
            }
        }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        bmap.onCreate(context, savedInstanceState)
    }

    override fun onResume() {
        super.onResume()
        bmap.onResume()
    }

    override fun onPause() {
        super.onPause()
        bmap.onPause()
    }

    override fun onDestroy() {
        super.onDestroy()
        bmap.onDestroy()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        root = inflater.inflate(R.layout.fragment_poi, container, false) as ViewGroup
        initView()
        bmap = root.findViewById(R.id.bmapView)



        initLiveData()

        LocationClient.setAgreePrivacy(true)
        SDKInitializer.setAgreePrivacy(context?.applicationContext, true)


        mLocationClient = LocationClient(requireActivity().applicationContext);
        // 声明LocationClient类
        mLocationClient.registerLocationListener(myListener);

        val option = LocationClientOption().apply {
            setIsNeedAddress(true)
            setNeedNewVersionRgc(true)
        }

        mLocationClient.locOption = option
        mLocationClient.start()
        isFetchingLocation.value = true


        // 创建poi检索实例，注册搜索事件监听
        poiSearch = PoiSearch.newInstance()
        poiSearch.setOnGetPoiSearchResultListener(PoiSearchResultListener())
        return root
    }

    private fun initLiveData() {
        isFetchingLocation.observe(viewLifecycleOwner) {
            poiSearchFetchingLocationProgress.invisibleIf(!it)
            poiSearchFetchingLocationText.invisibleIf(!it)
            poiSearchSubmit.invisibleIf(it)
            poiSearchInput.invisibleIf(it)

            poiFetchingPoiProgress.invisibleIf(!it)
            bmap.invisibleIf(it)
        }

        poiResData.observe(viewLifecycleOwner) {
//            Log.v("dydy", "sssssubmited!")
            bmap.visibility = View.INVISIBLE
            poiSearchResAdapter.submitList(it)
            poiSearchResAdapter.notifyDataSetChanged()
        }

        isFetchingPoi.observe(viewLifecycleOwner) {
            poiFetchingPoiProgress.invisibleIf(!it)
            bmap.invisibleIf(it)
        }
    }

    private fun initView() {
        poiSearchInput = root.findViewById(R.id.poi_search_input)
        poiSearchSubmit = root.findViewById(R.id.poi_search_submit)
        poiSearchRes = root.findViewById(R.id.poi_search_res)
        poiSearchFetchingLocationProgress =
            root.findViewById(R.id.poi_search_fetching_location_progress)
        poiSearchFetchingLocationText = root.findViewById(R.id.poi_search_fetching_location_text)
        poiFetchingPoiProgress = root.findViewById(R.id.fetching_poi_progress)

        poiSearchInput.setOnEditorActionListener { v, actionId, event ->
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                submitSearch()
                return@setOnEditorActionListener true

            }
            return@setOnEditorActionListener false
        }

        poiSearchSubmit.setOnClickListener {
            return@setOnClickListener submitSearch()
        }

        poiSearchRes.apply {
            addItemDecoration(
                DividerItemDecoration(
                    requireContext(),
                    DividerItemDecoration.VERTICAL
                )
            )
            layoutManager = LinearLayoutManager(requireContext())
            poiSearchResAdapter = PoiSearchAdapter(this@PoiSearchFragment)
            adapter = poiSearchResAdapter
            loadMoreOnScrollListener =
                PoiLoadMoreOnScrollListener(layoutManager as LinearLayoutManager)

            addOnScrollListener(loadMoreOnScrollListener)
        }

        loadingDialog = AlertDialog.Builder(requireContext())
            .setView(ProgressBar(requireContext()))
            .setTitle("正在算路")
            .create()


    }

    private fun submitSearch() {
        val keyword = poiSearchInput.text.toString()
        if (keyword.isEmpty()) {
            toast("请输入检索关键字")
            return
        }
        this.keyword = keyword
        isSubmitted = true
        loadedPage = -1
        poiResData.value = emptyList<PoiSearchListItem>()
        loadMoreOnScrollListener.clear()

        isFetchingPoi.value = true
        // 发起请求
        poiSearch.searchInCity(
            PoiCitySearchOption()
                .city(city)
                .keyword(keyword)
                .pageNum(++loadedPage) // 分页编号
                .cityLimit(false)
                .scope(1)
        )
    }

    fun onPoiItemClicked(item: PoiSearchListItem) {
        val start = BNRoutePlanNode.Builder()
            .latitude(curLocation!!.latitude)
            .longitude(curLocation!!.longitude)
            .name(curLocation!!.buildingName)
            .description(curLocation!!.addrStr)
            .build()
        val end = BNRoutePlanNode.Builder()
            .latitude(item.latitude)
            .longitude(item.longitude)
            .name(item.name)
            .description(item.description)
            .build()
        Log.e("TAG", "onPoiItemClicked: " + start.latitude + start.longitude)
        Log.e("TAG", "onPoiItemClicked: " + end.latitude + end.longitude)

        var temPointList: List<BNRoutePlanNode> = ArrayList()
//        temPointList.

        try {
            BaiduNaviManagerFactory.getRoutePlanManager().routePlanToNavi(
                listOf(start, end),
                IBNRoutePlanManager.RoutePlanPreference.ROUTE_PLAN_PREFERENCE_DEFAULT,
                null,
                handler
            )
        } catch (e: Exception) {
            Log.e(DEBUG_PRE, "onPoiItemClicked: $e")
        }
        loadingDialog.show()

    }

    private fun checkKeyWord(): Boolean {
        val keyword = poiSearchInput.text.toString()
        if (keyword.isEmpty()) {
            return false
        }
        return true

    }

    inner class PoiLoadMoreOnScrollListener(private val layoutManager: LinearLayoutManager) :
        LoadMoreOnScrollListener(layoutManager) {
        override fun onLoadMore(currentPage: Int) {
            if (currentPage > loadedPage && isSubmitted) {
                isFetchingPoi.value = true
                Log.v("dydy", loadedPage.toString())
                poiSearch.searchInCity(
                    PoiCitySearchOption()
                        .city(city)
                        .keyword(keyword)
                        .pageNum(++loadedPage) // 分页编号
                        .cityLimit(false)
                        .scope(1)
                )
            }
        }

    }


    inner class PoiSearchResultListener : OnGetPoiSearchResultListener {
        override fun onGetPoiResult(res: PoiResult) {
            isFetchingPoi.value = false
            toast(res.error.name)
//            Log.e(TAG, "onGetPoiResult: "+res.error)
            if (res.error == SearchResult.ERRORNO.RESULT_NOT_FOUND) {
                toast("未找到结果")
                return
            }
            if (res.error == SearchResult.ERRORNO.NO_ERROR) {
                toast("no error")
                val pois = res.allPoi
                val items = pois.map {
                    PoiSearchListItem(
                        it.uid, it.name, it.address, it.location.latitude, it.location.longitude
                    )
                }
//                Log.v("dydy", items.toString())
                val newValue = mutableListOf<PoiSearchListItem>()
                newValue.addAll(poiResData.value!!)
                newValue.addAll(items)
                poiResData.value = newValue
            }


        }

        override fun onGetPoiDetailResult(p0: PoiDetailResult?) {}
        override fun onGetPoiDetailResult(p0: PoiDetailSearchResult?) {}
        override fun onGetPoiIndoorResult(p0: PoiIndoorResult?) {}
    }

    inner class LocationListener : BDAbstractLocationListener() {
        override fun onReceiveLocation(location: BDLocation) {
            isFetchingLocation.value = false
            city = location.city
            curLocation = location

            val locData = MyLocationData.Builder()
                .accuracy(location.radius)
                .direction(location.direction)
                .latitude(location.latitude)
                .longitude(location.longitude)
                .build()
            bmap.map.isMyLocationEnabled = true

            val status = MapStatus.Builder()
                .zoom(18.0f)
                .target(LatLng(location.latitude, location.longitude))
                .build()
            bmap.map.setMapStatus(MapStatusUpdateFactory.newMapStatus(status))
            bmap.map.setMyLocationData(locData)
//            toast(location.city)
        }

    }

    private fun toast(text: String) {
        (activity as StageActivity).toast(text)
    }


}