webpackJsonp([5],{104:function(e,r,t){"use strict";t.d(r,"a",function(){return n});var n={login:"login"}},117:function(e,r,t){"use strict";Object.defineProperty(r,"__esModule",{value:!0});var n=t(2),o=t(9),a=t(83),i=t.n(a),l=t(1),s=t.n(l);n.default.use(o.default),n.default.use(s.a.config);var c=s.a.generateStore();new n.default({el:"#app",store:c,template:"<App/>",components:{App:i.a}})},150:function(e,r,t){"use strict";Object.defineProperty(r,"__esModule",{value:!0});var n=t(1),o=t.n(n),a=t(104),i=o.a.panel,l=o.a.vform;r.default={data:function(){return{formActions:{init:function(e){return{rules:{items:[{name:"username",label:"账号",type:"text",validate:[{errorMsg:"不能为空",regex:"^\\S+$"},{errorMsg:"请输入正确的账号，手机号或者Email",regex:"^(\\w{5,40})|(\\w+([-+.]\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*)|((13[0-9]|14[5|7]|15[0|1|2|3|5|6|7|8|9]|(17|18)[0|1|2|3|5|6|7|8|9])\\d{8})$"}],placeholder:"账号/手机号/Email",locked:!1,error:!0},{name:"password",label:"密码",type:"password",placeholder:"密码",validate:[{errorMsg:"不能为空",regex:"^\\S+$"}]}],action:{save:{label:"登录"}}}}}},formActionUrls:{directSaveUrl:a.a.login},authenticationError:!1}},methods:{},components:{panel:i,vform:l},mounted:function(){var e=new RegExp("[?&]"+name+"(=([^&#]*)|&|#|$)"),r=e.exec(window.location.href);r&&r[2]&&(this.authenticationError=!0)}}},204:function(e,r){},267:function(e,r){e.exports={render:function(){var e=this,r=e.$createElement,t=e._self._c||r;return t("panel",{attrs:{id:"login-wrapper",col:6}},[t("h1",{slot:"header"},[e._v("用户登录")]),e._v(" "),e.authenticationError?t("p",{staticClass:"error"},[e._v("\n    用户名或密码错误,请重新输入\n  ")]):e._e(),e._v(" "),t("vform",{attrs:{id:"login-form",actionUrls:e.formActionUrls,actions:e.formActions}})],1)},staticRenderFns:[]}},83:function(e,r,t){t(204);var n=t(0)(t(150),t(267),null,null);e.exports=n.exports}},[117]);
//# sourceMappingURL=loginOthers.004a21aaafc47881c44d.js.map