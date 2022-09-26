package change.aaa.rtmpdemo.navi

import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

abstract class LoadMoreOnScrollListener(private val mLinearLayoutManager: LinearLayoutManager) :
    RecyclerView.OnScrollListener() {
    private var currentPage = 1

    /**
     * 已经加载出来的item个数
     */
    private var totalItemCount = 0

    /**
     * 上一个totalItemCount
     */
    private var previousTotal = 0

    /**
     * 屏幕上可见item个数
     */
    private var visibleItemCount = 0

    /**
     * 屏幕可见item的第一个
     */
    private var firstVisiableItem = 0

    /**
     * 是否正在上拉数据
     */
    private var isPulling = true
    override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
        super.onScrolled(recyclerView, dx, dy)
        // 可见item个数
        visibleItemCount = recyclerView.childCount
        // item总数
        totalItemCount = mLinearLayoutManager.itemCount
        // 第一个可见item
        firstVisiableItem = mLinearLayoutManager.findFirstVisibleItemPosition()
        if (isPulling) {
            if (totalItemCount > previousTotal) {
                // 数据成功获取才会执行 说明数据已经加载结束
                isPulling = false
                previousTotal = totalItemCount
            }
        }

        //如果获取数据失败，则不会这行此处，因为loading始终为true
        //当最后一个item可见时，执行加载
        if (!isPulling && totalItemCount - visibleItemCount <= firstVisiableItem) {
            currentPage++
            onLoadMore(currentPage)
            isPulling = true
        }
    }

    fun clear() {
        currentPage = 1
        totalItemCount = 0
        previousTotal = 0
        visibleItemCount = 0
        firstVisiableItem = 0
        isPulling = true

    }

    abstract fun onLoadMore(currentPage: Int)
}