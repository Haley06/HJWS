(function(e){function t(t){for(var a,o,c=t[0],i=t[1],u=t[2],l=0,f=[];l<c.length;l++)o=c[l],Object.prototype.hasOwnProperty.call(r,o)&&r[o]&&f.push(r[o][0]),r[o]=0;for(a in i)Object.prototype.hasOwnProperty.call(i,a)&&(e[a]=i[a]);h&&h(t);while(f.length)f.shift()();return s.push.apply(s,u||[]),n()}function n(){for(var e,t=0;t<s.length;t++){for(var n=s[t],a=!0,o=1;o<n.length;o++){var c=n[o];0!==r[c]&&(a=!1)}a&&(s.splice(t--,1),e=i(i.s=n[0]))}return e}var a={},o={app:0},r={app:0},s=[];function c(e){return i.p+"js/"+({}[e]||e)+"."+{"chunk-06b338e2":"73475274","chunk-07d85ad7":"eb2061a9","chunk-113c9e1c":"75d4efa5","chunk-18bdc288":"8ab72f3a","chunk-1d732b3e":"a51f51fb","chunk-1ff538e0":"ea33c969","chunk-2d0c0a65":"e23c4486","chunk-65f75dc0":"b0c33f75","chunk-74b67010":"ec6227bf","chunk-2d208d90":"d9ec9672","chunk-331d1566":"08b55502","chunk-387d82f7":"61264da6","chunk-3944a506":"90c78cfe","chunk-4532374f":"e9a9742b","chunk-458409b9":"446a17b1","chunk-485bc0ac":"685a14e1","chunk-551c3e5e":"cf7754f0","chunk-55a3411e":"f7636748","chunk-659b7af9":"d03b99f0","chunk-6b789896":"5a4a31e0","chunk-73b023fc":"9576d5b3","chunk-c8f3f7f0":"94e4128e","chunk-d6ebb558":"9dddaed4","chunk-f19f83be":"24b1d151","chunk-f74472b8":"56c12add","chunk-ff16c0f2":"b23aea36"}[e]+".js"}function i(t){if(a[t])return a[t].exports;var n=a[t]={i:t,l:!1,exports:{}};return e[t].call(n.exports,n,n.exports,i),n.l=!0,n.exports}i.e=function(e){var t=[],n={"chunk-06b338e2":1,"chunk-07d85ad7":1,"chunk-113c9e1c":1,"chunk-18bdc288":1,"chunk-1d732b3e":1,"chunk-1ff538e0":1,"chunk-65f75dc0":1,"chunk-74b67010":1,"chunk-331d1566":1,"chunk-387d82f7":1,"chunk-3944a506":1,"chunk-4532374f":1,"chunk-458409b9":1,"chunk-485bc0ac":1,"chunk-551c3e5e":1,"chunk-55a3411e":1,"chunk-659b7af9":1,"chunk-6b789896":1,"chunk-73b023fc":1,"chunk-c8f3f7f0":1,"chunk-d6ebb558":1,"chunk-f19f83be":1,"chunk-f74472b8":1,"chunk-ff16c0f2":1};o[e]?t.push(o[e]):0!==o[e]&&n[e]&&t.push(o[e]=new Promise((function(t,n){for(var a="css/"+({}[e]||e)+"."+{"chunk-06b338e2":"9b81156e","chunk-07d85ad7":"d2ad5a07","chunk-113c9e1c":"fa0b6f15","chunk-18bdc288":"cef9efd0","chunk-1d732b3e":"c06465fb","chunk-1ff538e0":"86c88a66","chunk-2d0c0a65":"31d6cfe0","chunk-65f75dc0":"32225183","chunk-74b67010":"97dc487f","chunk-2d208d90":"31d6cfe0","chunk-331d1566":"08c60fbc","chunk-387d82f7":"383e9dad","chunk-3944a506":"17933fd1","chunk-4532374f":"31d4dfc6","chunk-458409b9":"1ec37718","chunk-485bc0ac":"cae6cc55","chunk-551c3e5e":"6abe5538","chunk-55a3411e":"68b8ba67","chunk-659b7af9":"89f090eb","chunk-6b789896":"0c202b22","chunk-73b023fc":"7055653a","chunk-c8f3f7f0":"1be96603","chunk-d6ebb558":"660a79f8","chunk-f19f83be":"94f1a2e3","chunk-f74472b8":"6ec68111","chunk-ff16c0f2":"c46bff59"}[e]+".css",r=i.p+a,s=document.getElementsByTagName("link"),c=0;c<s.length;c++){var u=s[c],l=u.getAttribute("data-href")||u.getAttribute("href");if("stylesheet"===u.rel&&(l===a||l===r))return t()}var f=document.getElementsByTagName("style");for(c=0;c<f.length;c++){u=f[c],l=u.getAttribute("data-href");if(l===a||l===r)return t()}var h=document.createElement("link");h.rel="stylesheet",h.type="text/css",h.onload=t,h.onerror=function(t){var a=t&&t.target&&t.target.src||r,s=new Error("Loading CSS chunk "+e+" failed.\n("+a+")");s.code="CSS_CHUNK_LOAD_FAILED",s.request=a,delete o[e],h.parentNode.removeChild(h),n(s)},h.href=r;var d=document.getElementsByTagName("head")[0];d.appendChild(h)})).then((function(){o[e]=0})));var a=r[e];if(0!==a)if(a)t.push(a[2]);else{var s=new Promise((function(t,n){a=r[e]=[t,n]}));t.push(a[2]=s);var u,l=document.createElement("script");l.charset="utf-8",l.timeout=120,i.nc&&l.setAttribute("nonce",i.nc),l.src=c(e);var f=new Error;u=function(t){l.onerror=l.onload=null,clearTimeout(h);var n=r[e];if(0!==n){if(n){var a=t&&("load"===t.type?"missing":t.type),o=t&&t.target&&t.target.src;f.message="Loading chunk "+e+" failed.\n("+a+": "+o+")",f.name="ChunkLoadError",f.type=a,f.request=o,n[1](f)}r[e]=void 0}};var h=setTimeout((function(){u({type:"timeout",target:l})}),12e4);l.onerror=l.onload=u,document.head.appendChild(l)}return Promise.all(t)},i.m=e,i.c=a,i.d=function(e,t,n){i.o(e,t)||Object.defineProperty(e,t,{enumerable:!0,get:n})},i.r=function(e){"undefined"!==typeof Symbol&&Symbol.toStringTag&&Object.defineProperty(e,Symbol.toStringTag,{value:"Module"}),Object.defineProperty(e,"__esModule",{value:!0})},i.t=function(e,t){if(1&t&&(e=i(e)),8&t)return e;if(4&t&&"object"===typeof e&&e&&e.__esModule)return e;var n=Object.create(null);if(i.r(n),Object.defineProperty(n,"default",{enumerable:!0,value:e}),2&t&&"string"!=typeof e)for(var a in e)i.d(n,a,function(t){return e[t]}.bind(null,a));return n},i.n=function(e){var t=e&&e.__esModule?function(){return e["default"]}:function(){return e};return i.d(t,"a",t),t},i.o=function(e,t){return Object.prototype.hasOwnProperty.call(e,t)},i.p="",i.oe=function(e){throw console.error(e),e};var u=window["webpackJsonp"]=window["webpackJsonp"]||[],l=u.push.bind(u);u.push=t,u=u.slice();for(var f=0;f<u.length;f++)t(u[f]);var h=l;s.push([0,"chunk-vendors"]),n()})({0:function(e,t,n){e.exports=n("56d7")},"164e":function(e,t){e.exports=echarts},"189c":function(e,t,n){},"1eaa":function(e,t,n){"use strict";n("f514")},"216c":function(e,t,n){"use strict";var a=function(){var e=this,t=e.$createElement,n=e._self._c||t;return n("div",{staticClass:"nav"},[n("van-icon",{staticClass:"icon",attrs:{name:"arrow-left",size:"25"},on:{click:e.goBack}}),n("span",{staticClass:"nav-title"},[e._t("default")],2),e._t("right")],2)},o=[],r={name:"Back",methods:{goBack:function(){this.$router.go(-1)}}},s=r,c=(n("e31b"),n("2877")),i=Object(c["a"])(s,a,o,!1,null,"2fcbdb40",null);t["a"]=i.exports},"2d5c":function(e,t,n){"use strict";n("189c")},"533f":function(e,t,n){"use strict";n("bacc")},"56d7":function(e,t,n){"use strict";n.r(t);n("e260"),n("e6cf"),n("cca6"),n("a79d");var a=n("8bbf"),o=n.n(a),r=function(){var e=this,t=e.$createElement,n=e._self._c||t;return n("div",{attrs:{id:"app"}},[n("router-view")],1)},s=[],c={name:"App",created:function(){var e=this;sessionStorage.getItem("store")&&this.$store.replaceState(Object.assign({},this.$store.state,JSON.parse(sessionStorage.getItem("store")))),window.addEventListener("beforeunload",(function(){sessionStorage.setItem("store",JSON.stringify(e.$store.state))}))}},i=c,u=(n("7c55"),n("2877")),l=Object(u["a"])(i,r,s,!1,null,null,null),f=l.exports,h=(n("d3b7"),n("782f"),function(){var e=this,t=e.$createElement,n=e._self._c||t;return n("div",{staticClass:"emergency"},[e.emptyList?n("van-empty",{attrs:{description:"暂未设置"}}):n("div",{staticStyle:{width:"100%"}},e._l(e.phoneList,(function(t,a){return" "!=t.name?n("div",{key:a,staticClass:"item"},[n("div",{staticClass:"svg"},[n("svg",{staticClass:"icon",attrs:{t:"1626437362913",viewBox:"0 0 1024 1024",version:"1.1",xmlns:"http://www.w3.org/2000/svg","p-id":"1399",width:"200",height:"200"}},[n("path",{attrs:{d:"M673.216263 657.80476c5.39794 4.623297 12.344143 7.522324 20.016893 7.522324 16.870226 0 30.540573-13.467733 30.540573-30.037107 0.023536-9.957793-4.975315-18.7439-12.622482-24.216541 0.051165-0.112564 0.051165-0.249687 0.051165-0.337691-30.413684-22.503527-62.424748-37.985126-92.715635-48.579415 72.471568-44.010353 121.804137-121.340579 121.804137-212.357526 0-138.034796-114.081245-247.242034-252.853845-247.242034-138.745994 0-252.801656 110.757546-252.801656 248.767783 0 89.49222 47.781236 168.32159 120.252805 210.832799C146.768203 636.465756 128.275014 847.282183 128.275014 850.331635c-1.550309 16.668635 10.770298 31.826869 27.739784 31.826869 16.943904 1.523703 32.362059-10.597359 33.911344-27.314089 0-10.622942 23.115464-241.156432 294.412356-256.325923 6.77224 0 104.459097-3.560082 188.777481 59.328223C673.165098 657.831366 673.190681 657.80476 673.216263 657.80476zM487.43707 537.864064c-104.809067 0-191.125969-83.392292-191.125969-188.06526 0-104.658641 86.317924-186.566116 191.125969-186.566116 104.860233 0 191.152575 83.417875 191.152575 188.079586C678.589644 455.959659 592.297302 537.864064 487.43707 537.864064zM851.847663 641.136126c-5.148253-1.297552-10.497075-2.086521-16.144702-2.086521-32.987299 0-60.126402 27.427676-60.126402 61.376882 0 27.702945 18.142196 51.229778 42.73429 58.790988-18.443048 30.823006-43.809785 57.214073-74.021877 76.282361-9.196453-21.680788-30.438243-36.83493-55.279001-36.83493-33.236985 0-60.151985 27.41335-60.151985 61.388139 0 3.921309 0.249687 7.834432 1.299599 11.506055 5.122671 28.203342 29.164227 49.619094 58.103326 49.883107 16.491603 0 25.589818-4.386914 33.685193-7.312546 87.068008-31.499411 151.593604-105.084337 171.360809-195.397249 0.823761-3.924379 2.548032-12.133342 2.548032-18.306948C895.854946 672.224168 877.137653 648.460951 851.847663 641.136126z","p-id":"1400",fill:"#9981B8"}})])]),n("div",{staticClass:"info"},[n("div",{staticClass:"name"},[e._v(e._s(t.name))]),n("div",{staticClass:"number"},[e._v(e._s(t.mobile))])])]):e._e()})),0),n("div",{staticClass:"edit"},[n("button",{on:{click:e.showPopup}},[e._v("编辑联系人")]),n("van-popup",{attrs:{position:"bottom"},model:{value:e.show,callback:function(t){e.show=t},expression:"show"}},[n("van-form",[e._l(e.phoneList,(function(t,a){return n("div",{key:a,staticClass:"phoneList"},[n("div",{staticClass:"text"},[e._v(" 联系人"+e._s(a+1)+" "),n("span",{class:{show:0===e.validateArr[a],hide:0!==e.validateArr[a]}},[e._v("请填写正确的信息")])]),n("van-field",{attrs:{name:"姓名",label:"姓名",placeholder:"输入联系人名称"},model:{value:t.name,callback:function(n){e.$set(t,"name",n)},expression:"item.name"}}),n("van-field",{attrs:{name:"联系方式",label:"联系方式",placeholder:"输入号码"},model:{value:t.mobile,callback:function(n){e.$set(t,"mobile",n)},expression:"item.mobile"}})],1)})),n("div",{staticStyle:{margin:"16px"}},[n("van-button",{attrs:{round:"",block:"",type:"info","native-type":"submit"},on:{click:e.submit}},[e._v("提交")])],1)],2)],1)],1)],1)}),d=[],p=(n("4160"),n("b0c0"),n("159b"),n("2909")),m=(n("96cf"),n("1da1")),b={name:"Emergency",data:function(){return{show:!1,serverId:"",emptyList:!0,phoneList:[{name:"李鑫磊",mobile:"18920251111"},{name:"",mobile:""},{name:"",mobile:""}],validateArr:[1,1,1]}},methods:{showPopup:function(){this.show=!this.show},validate:function(){var e=this;this.validateArr=[0,0,0];var t=!1;return this.phoneList.forEach((function(n,a){var o=/^[\u4E00-\u9FA5\uf900-\ufa2d·s]{2,20}$/,r=/^1[3|5|7|8]\d{9}$/,s=o.test(n.name)&&r.test(n.mobile),c=""!==n.name&&""!==n.mobile,i=""===n.name&&""===n.mobile;c&&(s?e.$set(e.validateArr,a,1):(t=!0,e.$set(e.validateArr,a,0))),i&&e.$set(e.validateArr,a,-1)})),!t},submit:function(){var e=this;return Object(m["a"])(regeneratorRuntime.mark((function t(){var n,a;return regeneratorRuntime.wrap((function(t){while(1)switch(t.prev=t.next){case 0:if(!e.validate()){t.next=6;break}return t.next=3,axios.create().post("http://39.105.171.41:3000/postInfo",{mobile:e.serverId,info:e.phoneList});case 3:n=t.sent,a=n.data,a.hasOwnProperty("state")&&!0===a.state?(e.showPopup(),vant.Notify({message:"提交成功",type:"success"})):vant.Toast({message:"提交失败",icon:"user-circle-o"});case 6:case"end":return t.stop()}}),t)})))()}},mounted:function(){var e=this;return Object(m["a"])(regeneratorRuntime.mark((function t(){var n,a;return regeneratorRuntime.wrap((function(t){while(1)switch(t.prev=t.next){case 0:return e.emptyList=Boolean(!e.phoneList.length),t.next=3,e.$axios.get("/userInfo");case 3:return e.serverId=t.sent.user.mobile,t.next=6,axios.create().get("http://39.105.171.41:3000/getInfo",{params:{mobile:e.serverId}});case 6:n=t.sent,a=n.data,e.phoneList=Object(p["a"])(a);case 9:case"end":return t.stop()}}),t)})))()}},v=b,g=(n("ab55"),Object(u["a"])(v,h,d,!1,null,"57e732eb",null)),k=g.exports,y=function(){var e=this,t=e.$createElement,n=e._self._c||t;return n("div",{staticClass:"dataChart"},[n("Nav",{attrs:{"is-show-back-arrow":!1}},[e._v("疲劳报表")]),n("div",{staticClass:"content"},[n("el-card",{staticClass:"box-card history"},[n("div",{staticClass:"history-title title"},[e._v("疲劳历史")]),n("div",{staticClass:"history-chart",attrs:{id:"history-chart-1"}})]),n("el-card",{staticClass:"box-card history"},[n("div",{staticClass:"history-title title"},[e._v("今日分析")]),n("div",{staticClass:"history-chart",attrs:{id:"history-chart-2"}})])],1)],1)},w=[],x=n("216c"),C={login:{path:"/login",successMsg:"登录成功",showType:0},register:{path:"/register/old",successMsg:"注册成功",showType:0},getUserInfo:{path:"/info",successMsg:"获取成功",showType:0},changeUserInfo:{path:"/info",successMsg:"获取成功",showType:0},getVercode:{path:"/login/vercode",successMsg:"获取成功",showType:1},postVercode:{path:"/vercode",successMsg:"登录成功",showType:0},getPhone:{path:"/phone",successMsg:"获取成功"},postPhone:{path:"/phone",successMsg:"提交成功",showType:0},getResult:{path:"/fatigue/query"},getUserByPhone:{path:"/monitored/selectByPhone",successMsg:"获取成功",showType:0},addFollow:{path:"/monitored/add",successMsg:"已发送申请",showType:0},getAllFollowUser:{path:"/monitored/userDriven",successMsg:"获取成功",showType:0},getCategories:{path:"/shop/getCategory"},getGoodsAll:{path:"/shop/getAll"}},_=C,I={name:"DataChart",components:{Nav:x["a"]},data:function(){return{}},methods:{getResult:function(e){var t=this;return Object(m["a"])(regeneratorRuntime.mark((function n(){return regeneratorRuntime.wrap((function(n){while(1)switch(n.prev=n.next){case 0:return n.abrupt("return",new Promise(function(){var n=Object(m["a"])(regeneratorRuntime.mark((function n(a,o){var r,s;return regeneratorRuntime.wrap((function(n){while(1)switch(n.prev=n.next){case 0:e.xAxis[0].data.forEach((function(e,n,a){a[n]=t.getDay(-(6-n)).day})),r=0;case 2:if(!(r<7)){n.next=10;break}return n.next=5,t.$axios.get(_.getResult.path,{params:{start:t.getDay(-(6-r)).seconds,end:t.getDay(-(5-r)).seconds}});case 5:s=n.sent,e.series[0].data[6-r]=s.count;case 7:r++,n.next=2;break;case 10:console.log(111),a(e);case 12:case"end":return n.stop()}}),n)})));return function(e,t){return n.apply(this,arguments)}}()));case 1:case"end":return n.stop()}}),n)})))()},getResult1:function(e){return e},getDay:function(e){var t=new Date,n=t.getTime()+864e5*e;t.setTime(n);t.getFullYear();var a=t.getMonth()+1;a<10&&(a=a);var o=t.getDate();return o<10&&(o=o),{day:a+"/"+o,seconds:n}}},mounted:function(){this.$init(this.getResult,"baseInfoOption1","history-chart-1"),this.$init(this.getResult1,"deviceState","history-chart-2")}},P=I,O=(n("1eaa"),Object(u["a"])(P,y,w,!1,null,"697c3620",null)),S=O.exports,T=function(){var e=this,t=e.$createElement,n=e._self._c||t;return n("div",{staticClass:"Moments"},[n("Nav",[n("b",[e._v("车友圈")]),n("van-icon",{staticClass:"right-icon",attrs:{slot:"right",name:"add-o",size:"25"},on:{click:function(t){return e.goPublish()}},slot:"right"})],1),n("div",{staticClass:"middle"},[e.focusSet.length?n("div",[e._m(0),n("div",{staticClass:"box"},e._l(e.focusSet,(function(t,a){return n("div",{key:a,staticClass:"boxItem",on:{click:e.goChatList}},[n("el-avatar",{attrs:{src:t.avatarUrl,size:50}}),n("span",[e._v(e._s(t.username))])],1)})),0)]):e._e()]),e._l(e.texts,(function(t,a){return n("ul",{key:a,staticClass:"main"},[n("li",{on:{click:function(n){return e.more(t)}}},[n("Moment",{attrs:{text:t.description,time:t.createTime.substring(5,16),name:t.username,avatarUrl:t.avatarUrl}})],1)])}))],2)},$=[function(){var e=this,t=e.$createElement,a=e._self._c||t;return a("div",{staticClass:"focus"},[a("span",[e._v("已关注")]),a("img",{attrs:{src:n("a70a"),alt:"arrow"}})])}],j=n("6507"),L={data:function(){return{texts:[],pageNum:1,totalPage:0,focusSet:[]}},mounted:function(){this.getAll()},methods:{getAll:function(){var e=this;this.$axios.get("/text/all",{params:{pageNum:this.pageNum,pageSize:4}}).then((function(t){var n;(console.log(t),"success"===t.msg)&&((n=e.texts).push.apply(n,Object(p["a"])(t.allText)),e.totalPage=t.totalPage)})),this.$axios.get("/monitored/user").then((function(t){e.focusSet=t.results}))},goPublish:function(){this.$router.push("/Publish")},goChatList:function(){this.$router.push("/chatList")},more:function(e){this.$router.push({path:"/Detail",query:{item:e}})},goBack:function(){this.$router.go(-1)}},components:{Moment:j["a"],Nav:x["a"]}},M=L,A=(n("bea8"),Object(u["a"])(M,T,$,!1,null,"0d049c29",null)),R=A.exports;o.a.use(VueRouter);var E=[{path:"/",redirect:"/login"},{path:"/login",name:"Login",component:function(){return n.e("chunk-331d1566").then(n.bind(null,"4b3f"))}},{path:"/register",name:"Register",component:function(){return n.e("chunk-551c3e5e").then(n.bind(null,"61d1"))}},{path:"/profile",name:"Profile",component:function(){return n.e("chunk-4532374f").then(n.bind(null,"7853"))}},{path:"/moreFunction",component:function(){return n.e("chunk-3944a506").then(n.bind(null,"bdf7"))}},{path:"/friendDrive",name:"FriendDrive",component:function(){return n.e("chunk-387d82f7").then(n.bind(null,"0ed4"))}},{path:"/addFriend",name:"AddFriend",component:function(){return n.e("chunk-659b7af9").then(n.bind(null,"8d41"))}},{path:"/settings",name:"Settings",component:function(){return n.e("chunk-18bdc288").then(n.bind(null,"e8df"))},children:[{path:"info",name:"PersonInfo",component:function(){return Promise.resolve().then(n.bind(null,"782f"))}},{path:"emergency",name:"Emergency",component:k},{path:"aboutUs",name:"AboutUs",component:function(){return n.e("chunk-55a3411e").then(n.bind(null,"a86d"))}},{path:"feedback",name:"Feedback",component:function(){return n.e("chunk-1ff538e0").then(n.bind(null,"5eaa"))}},{path:"useGuidance",name:"useGuidance",component:function(){return n.e("chunk-2d208d90").then(n.bind(null,"a715"))}}]},{path:"/dataChart",name:"DaraChart",component:S},{path:"/Publish",name:"Publish",component:function(){return n.e("chunk-f19f83be").then(n.bind(null,"4ada"))}},{path:"/Detail",name:"Detail",component:function(){return n.e("chunk-458409b9").then(n.bind(null,"77ae"))}},{path:"/Moments",name:"Moments",component:R},{path:"/AllText",name:"AllText",component:function(){return n.e("chunk-f74472b8").then(n.bind(null,"b5a4"))}},{path:"/Detail",name:"Detail",component:function(){return n.e("chunk-458409b9").then(n.bind(null,"77ae"))}},{path:"/chat",name:"chat",component:function(){return n.e("chunk-1d732b3e").then(n.bind(null,"edd2"))}},{path:"/chatList",name:"chatList",component:function(){return n.e("chunk-485bc0ac").then(n.bind(null,"2717"))}},{path:"/mall",name:"mall",component:function(){return n.e("chunk-ff16c0f2").then(n.bind(null,"bd12"))}},{path:"/mallDetails",component:function(){return n.e("chunk-73b023fc").then(n.bind(null,"6468"))}},{path:"/cart",component:function(){return n.e("chunk-6b789896").then(n.bind(null,"8b15"))}},{path:"/orderFrom",component:function(){return n.e("chunk-113c9e1c").then(n.bind(null,"003a"))}},{path:"/address",component:function(){return n.e("chunk-c8f3f7f0").then(n.bind(null,"ed2e"))}},{path:"/addAddress",component:function(){return Promise.all([n.e("chunk-2d0c0a65"),n.e("chunk-74b67010")]).then(n.bind(null,"65d1"))}},{path:"/categoryPage",component:function(){return n.e("chunk-06b338e2").then(n.bind(null,"dc0f"))}},{path:"/editAddress",component:function(){return Promise.all([n.e("chunk-2d0c0a65"),n.e("chunk-65f75dc0")]).then(n.bind(null,"39a9"))}},{path:"/music",name:"music",component:function(){return n.e("chunk-d6ebb558").then(n.bind(null,"f6b1"))}},{path:"/musicList",name:"musicList",component:function(){return n.e("chunk-07d85ad7").then(n.bind(null,"5764"))}}],U=new VueRouter({mode:"hash",base:"",routes:E});U.beforeEach((function(e,t,n){n()}));var D=U,N=n("5880"),F=n.n(N),z={namespaced:!0,state:function(){return{userInfo:{}}},mutations:{userInfoChange:function(e,t){e.userInfo=t}},actions:{},getters:{}},B=z;o.a.use(F.a);var V=new F.a.Store({state:{mobile:"",addFriendsList:[]},mutations:{setMobile:function(e,t){e.mobile=t},userAddFriends:function(e,t){e.addFriendsList.push(t)}},actions:{},modules:{user:B}});n("a9e3");function q(e,t,n){e&&!e.hasOwnProperty("showType")||(t?vant.Toast({message:n,icon:"user-circle-o"}):e.showType?vant.Notify({message:e.successMsg,type:"success"}):vant.Toast({message:e.successMsg,icon:"success"}))}axios.defaults.baseURL="http://49.232.78.210:8089/app/",axios.defaults.timeout=1e4;var H={1:function(e){for(var t in _)_[t].path===e.config.url&&q(_[t],0);return e.data},200:function(e){return e.data},401:function(e){q(null,1,"验证失败"),J()},404:function(e){q(null,1,"404")},networkFail:function(){q(null,1,"网络错误")},default:function(e){return 200==e.data.code?e.data:(q(null,1,e.data.msg),Promise.reject(e))}};function J(){this.localStorage.clear()}function G(e,t){var n=new FormData;n.append("file",e);var a={headers:{"Content-Type":"multipart/form-data"}};return new Promise((function(e,o){axios.post(t,n,a).then((function(t){t&&t.data&&1===t.data.status?e(t.data):vant.Toast({message:"请求出错",icon:"user-circle-o"})})).catch((function(e){vant.Toast({message:"系统异常"}),o(e)}))}))}axios.interceptors.request.use((function(e){try{e.headers.token=dsbrige.call("getToken")}catch(n){}var t=window.localStorage.getItem("token");return t&&(e.headers.token=t),e}),(function(e){return window.navigator.onLine?(H["default"](),Promise.error(e)):H["networkFail"]()})),axios.interceptors.response.use((function(e){return 1===Number(e.data.code+1)?H[1](e):H["default"](e)}),(function(e){return window.navigator.onLine?H["default"](e):H["networkFail"]()})),axios.upLoaderImg=G;var W=axios,K=n("164e"),Y={baseInfoOption1:{color:["rgba(153, 129, 184, 0.95)"],tooltip:{trigger:"axis",axisPointer:{type:"shadow"}},grid:{bottom:"0",top:"20",containLabel:!0},xAxis:[{type:"category",data:["","","","","","",""],axisLine:{show:!0,lineStyle:{color:"rgb(158,158,158)"}},axisTick:{show:!1}}],yAxis:[{type:"value",axisLine:{show:!0,lineStyle:{color:"rgb(125,125,125)"}},splitLine:{show:!1}}],series:[{name:"疲劳监测次数",type:"line",barWidth:"60%",data:[0,0,0,0,0,0,0]}]},deviceState:{tooltip:{trigger:"item"},legend:{left:"center"},series:[{name:"访问来源",type:"pie",radius:["40%","70%"],avoidLabelOverlap:!1,itemStyle:{borderRadius:6,borderColor:"#fff",borderWidth:2},label:{show:!1,position:"center"},emphasis:{label:{show:!0,fontSize:"20",fontWeight:"bold"}},labelLine:{show:!1},data:[{value:0,name:"安全驾驶"},{value:0,name:"疲劳驾驶"}]}]}};function Q(e,t,n){return X.apply(this,arguments)}function X(){return X=Object(m["a"])(regeneratorRuntime.mark((function e(t,n,a){var o,r,s;return regeneratorRuntime.wrap((function(e){while(1)switch(e.prev=e.next){case 0:if(o=Y[n],s=K["init"](document.getElementById(a)),s.setOption(o),"function"!=typeof t){e.next=8;break}return console.log(o),e.next=7,t(o);case 7:r=e.sent;case 8:s.setOption(r),setTimeout((function(){s.resize()}),0),window.onresize=function(){s.resize()};case 11:case"end":return e.stop()}}),e)}))),X.apply(this,arguments)}var Z=Q,ee=n("7520"),te=n.n(ee);o.a.prototype.$Message=function(e){var t=arguments.length>1&&void 0!==arguments[1]?arguments[1]:"cross";vant.Toast({message:e,icon:t})},o.a.config.productionTip=!1,o.a.prototype.$axios=W,o.a.prototype.$url=_,o.a.prototype.$store=V,o.a.prototype.$dsbrige=te.a,o.a.prototype.$init=Z,new o.a({render:function(e){return e(f)},router:D,store:V}).$mount("#app")},5880:function(e,t){e.exports=Vuex},"5ba8":function(e,t,n){},"61cb":function(e,t,n){"use strict";n("e979")},6507:function(e,t,n){"use strict";var a=function(){var e=this,t=e.$createElement,n=e._self._c||t;return n("div",{staticClass:"Moment"},[n("div",{staticClass:"top"},[n("div",{staticClass:"Navleft"},[n("el-avatar",{attrs:{size:35,src:e.avatarUrl}}),n("span",{staticClass:"name"},[e._v(e._s(e.name))])],1),n("div",{staticClass:"Navright"},[e._v(e._s(e.timeTurn))])]),e._m(0)])},o=[function(){var e=this,t=e.$createElement,n=e._self._c||t;return n("div",{staticClass:"text"},[n("span",[e._v(" 当展示类型为图片的时候，图片加载失败的 fallback 行为当展示类型为图片的时候，图片加载失败的 fallback 行为当展示类型为图片的时候，图片加载失败的 fallback 行为当展示类型为图片的时候，图片加载失败的 fallback 行为当展示类型为图片的时候，图片加载失败的 fallback 行为当展示类型为图片的时候，图片加载失败的 fallback 行为 ")])])}],r=(n("4de4"),n("a15b"),n("ac1f"),n("5319"),n("1276"),{name:"Moment",props:["text","time","name","avatarUrl"],methods:{turnTime:function(e){var t=e.substring(0,5);return t=t.split("").filter((function(e){return"0"!=e})).join("").replace("-","月")+"日"+e.substring(5),t}},created:function(){this.timeTurn=this.turnTime(this.time)}}),s=r,c=(n("533f"),n("2877")),i=Object(c["a"])(s,a,o,!1,null,"00c1f82c",null);t["a"]=i.exports},"782f":function(e,t,n){"use strict";n.r(t);var a=function(){var e=this,t=e.$createElement,n=e._self._c||t;return n("div",{staticClass:"person-info"},[n("van-uploader",{ref:"uploader",attrs:{"after-read":e.afterRead,multiple:"","max-count":1}},[n("user-info-card-vue",{attrs:{leftText:"头像",cardHeight:"100"}},[n("img",{staticClass:"userHeadImg",attrs:{src:e.userInfoCopy.avatarUrl,alt:""}})])],1),n("div",{on:{click:function(t){e.choiceShow.username=!0}}},[n("user-info-card-vue",{attrs:{leftText:"昵称"}},[n("div",{staticClass:"userInfo"},[e._v(e._s(e.userInfoCopy.username))])])],1),n("div",{on:{click:function(t){e.choiceShow.sex=!0}}},[n("user-info-card-vue",{attrs:{leftText:"性别"}},[n("div",{staticClass:"userInfo"},[e._v(e._s(e.userInfoCopy.sex?"男":"女"))])])],1),n("div",[n("user-info-card-vue",{attrs:{leftText:"身份"}},[n("div",{staticClass:"userInfo"},[e._v(e._s(e.userInfoCopy.role?"VIP":"普通用户"))])])],1),n("div",{on:{click:function(t){e.choiceShow.introduction=!0}}},[n("user-info-card-vue",{attrs:{leftText:"简介"}},[n("div",{staticClass:"userInfo van-ellipsis"},[e._v(e._s(e.userInfoCopy.introduction))])])],1),n("div",{staticClass:"preserve"},[n("button",{class:[e.isPreserve?"isPreserve":"noPreserve"],on:{click:e.changeUserInfo}},[e._v(" 保存 ")])]),n("van-popup",{style:{height:"30%"},attrs:{position:"bottom",round:"",closeable:""},on:{open:e.openPopup,close:e.closePopup},model:{value:e.choiceShow.username,callback:function(t){e.$set(e.choiceShow,"username",t)},expression:"choiceShow.username"}},[n("van-field",{attrs:{rows:"5",autosize:"",type:"textarea"},model:{value:e.userInfoCopy.username,callback:function(t){e.$set(e.userInfoCopy,"username",t)},expression:"userInfoCopy.username"}})],1),n("van-popup",{style:{height:"30%"},attrs:{position:"bottom",round:"",closeable:""},on:{click:e.closePopup},model:{value:e.choiceShow.sex,callback:function(t){e.$set(e.choiceShow,"sex",t)},expression:"choiceShow.sex"}},[n("van-radio-group",{model:{value:e.userInfoCopy.sex,callback:function(t){e.$set(e.userInfoCopy,"sex",t)},expression:"userInfoCopy.sex"}},[n("van-cell-group",[n("van-cell",{attrs:{title:"男",clickable:""},on:{click:function(t){e.userInfoCopy.sex=1}},scopedSlots:e._u([{key:"right-icon",fn:function(){return[n("van-radio",{attrs:{name:1}})]},proxy:!0}])}),n("van-cell",{attrs:{title:"女",clickable:""},on:{click:function(t){e.userInfoCopy.sex=0}},scopedSlots:e._u([{key:"right-icon",fn:function(){return[n("van-radio",{attrs:{name:0}})]},proxy:!0}])})],1)],1)],1),n("van-popup",{style:{height:"30%"},attrs:{position:"bottom",round:"",closeable:""},on:{close:e.closePopup},model:{value:e.choiceShow.introduction,callback:function(t){e.$set(e.choiceShow,"introduction",t)},expression:"choiceShow.introduction"}},[n("van-field",{attrs:{rows:"5",autosize:"",type:"textarea"},model:{value:e.userInfoCopy.introduction,callback:function(t){e.$set(e.userInfoCopy,"introduction",t)},expression:"userInfoCopy.introduction"}})],1)],1)},o=[],r=(n("d3b7"),n("3ca3"),n("ddb0"),n("2b3d"),n("96cf"),n("1da1")),s=n("5530"),c=n("5880");n("7039");function i(e,t){var n=Object.getOwnPropertyNames(e),a=Object.getOwnPropertyNames(t);if(n.length!==a.length)return!1;for(var o=0;o<n.length;o++){var r=n[o],s=e[r],c=t[r];if(s!==c)return!1}return!0}var u=i,l=function(){var e=this,t=e.$createElement,n=e._self._c||t;return n("div",{staticClass:"userHead infoItem",style:"height:"+e.cardHeight+"px"},[n("span",{staticClass:"left"},[e._v(e._s(e.leftText))]),n("div",{staticClass:"right"},[e._t("default"),n("van-icon",{staticClass:"icon",attrs:{name:"arrow"}})],2)])},f=[],h={props:{leftText:{type:String,required:!0},cardHeight:{type:String,required:!1,default:"50"}}},d=h,p=(n("2d5c"),n("2877")),m=Object(p["a"])(d,l,f,!1,null,"74ff6320",null),b=m.exports,v={name:"PersonInfo",components:{userInfoCardVue:b},computed:Object(s["a"])({},Object(c["mapState"])("user",["userInfo"])),data:function(){return{isPreserve:!0,userInfoCopy:{},userInfoPopup:{},choiceShow:{username:!1,sex:!1,introduction:!1},file:new FormData}},methods:Object(s["a"])(Object(s["a"])({},Object(c["mapMutations"])("user",["changeUserInfo"])),{},{isObjectValueEqual:u,changeUserInfo:function(){var e=this;return Object(r["a"])(regeneratorRuntime.mark((function t(){var n;return regeneratorRuntime.wrap((function(t){while(1)switch(t.prev=t.next){case 0:return t.next=2,e.$axios.post(e.$url.changeUserInfo.path,e.userInfoCopy);case 2:return t.sent,t.next=5,fetch("api/app/photo/upload",{method:"POST",headers:{token:localStorage.getItem("token")},body:e.file}).catch((function(e){console.log(e)}));case 5:n=t.sent,console.log(n);case 7:case"end":return t.stop()}}),t)})))()},afterRead:function(e){var t=this;return Object(r["a"])(regeneratorRuntime.mark((function n(){var a,o;return regeneratorRuntime.wrap((function(n){while(1)switch(n.prev=n.next){case 0:t.file.append("file",e.file),a=window.URL||window.webkitURL,o=a.createObjectURL(e.file),t.userInfoCopy.avatarUrl=o;case 4:case"end":return n.stop()}}),n)})))()},openPopup:function(){this.userInfoPopup=Object.assign({},this.userInfoCopy)},closePopup:function(){}}),created:function(){this.userInfoCopy=this.userInfo}},g=v,k=(n("61cb"),Object(p["a"])(g,a,o,!1,null,"4ecad352",null));t["default"]=k.exports},"7c55":function(e,t,n){"use strict";n("5ba8")},"8bbf":function(e,t){e.exports=Vue},a70a:function(e,t,n){e.exports=n.p+"img/arrow.d638ac5e.svg"},ab55:function(e,t,n){"use strict";n("ab73")},ab73:function(e,t,n){},bacc:function(e,t,n){},bea8:function(e,t,n){"use strict";n("c45f")},c45f:function(e,t,n){},d2de:function(e,t,n){},e31b:function(e,t,n){"use strict";n("d2de")},e979:function(e,t,n){},f514:function(e,t,n){}});
//# sourceMappingURL=app.8921c95d.js.map