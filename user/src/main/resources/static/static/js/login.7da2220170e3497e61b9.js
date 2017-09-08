webpackJsonp([4],{102:function(e,r,t){t(279);var a=t(0)(t(185),t(376),null,null);e.exports=a.exports},135:function(e,r,t){"use strict";Object.defineProperty(r,"__esModule",{value:!0}),function(e){var r=t(5),a=t(96),o=t(11),n=t(102),s=t.n(n),i=t(1),l=t.n(i);r.default.use(o.default),r.default.use(l.a.config),e.store=l.a.generateStore(),new r.default({el:"#app",router:a.a,store:e.store,template:"<App/>",components:{App:s.a}})}.call(r,t(2))},159:function(e,r,t){"use strict";Object.defineProperty(r,"__esModule",{value:!0});var a=t(1),o=t.n(a),n=t(73),s=o.a.panel,i=o.a.vform;r.default={data:function(){return{formActions:{init:function(e){return{rules:{items:[{name:"username",label:"账号",type:"text",validate:[{errorMsg:"不能为空",regex:"^\\S+$"},{errorMsg:"请输入正确的账号，手机号或者Email",regex:"^(\\w{5,40})|(\\w+([-+.]\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*)|((13[0-9]|14[5|7]|15[0|1|2|3|5|6|7|8|9]|(17|18)[0|1|2|3|5|6|7|8|9])\\d{8})$"}],placeholder:"账号/手机号/Email",locked:!1,error:!0},{name:"password",label:"密码",type:"password",placeholder:"密码",validate:[{errorMsg:"不能为空",regex:"^\\S+$"}]}],action:{save:{label:"登录"},others:[{key:"registration",label:"没有账号？请注册!"}]}}}}},formActionUrls:{directSaveUrl:n.a.login,registration:n.a.registrationPage},authenticationError:!1}},methods:{},components:{panel:s,vform:i},mounted:function(){this.$route.query.authentication_error&&(this.authenticationError=!0)}}},160:function(e,r,t){"use strict";Object.defineProperty(r,"__esModule",{value:!0}),function(e){var a=t(3),o=t.n(a),n=t(1),s=t.n(n),i=t(73),l=s.a.panel,c=s.a.vform,d=s.a.modalTpl;r.default={data:function(){return{succeed:!1,formActions:{init:function(e){return{rules:{items:[{name:"username",label:"账号",type:"text",validate:[{errorMsg:"不能为空",regex:"^\\S+$"},{errorMsg:"账号由英文，数字和 _ 组成，并在5-40个字符之间",regex:"^\\w{5,40}$"}],placeholder:"账号"},{name:"password",label:"密码",type:"password",placeholder:"密码",validate:[{errorMsg:"不能为空",regex:"^\\S+$"},{errorMsg:"至少包含数字，字母以及特殊字符【!@#$%^&*_】中任意两种,并在5-15字符之间",regex:"^(?![a-zA-Z]+$)(?!\\d+$)(?![!@#$%^&*_]+$)[\\w!@#$%^&*]{5,15}$"}],ruleChange:!0},{name:"repassword",label:"重复密码",type:"password",placeholder:"重复密码",ruleChange:!0},{name:"name",label:"姓名",type:"text",validate:[{errorMsg:"不能为空",regex:"^\\S+$"},{errorMsg:"账号由英文，数字和 _ 组成，并在2-40个字符之间",regex:"^\\S{2,40}$"}],placeholder:"姓名"},{name:"phone",label:"电话",type:"text",validate:[{errorMsg:"不能为空",regex:"^\\S+$"},{errorMsg:"请输入正确的手机号",regex:"^(13[0-9]|14[5|7]|15[0|1|2|3|5|6|7|8|9]|(17|18)[0|1|2|3|5|6|7|8|9])\\d{8}$"}],placeholder:"手机号"},{name:"email",label:"Email",type:"text",validate:[{errorMsg:"不能为空",regex:"^\\S+$"},{errorMsg:"请输入正确的Email",regex:"^\\w+([-+.]\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*$"}],placeholder:"Email"},{name:"photoFiles",label:"头像",type:"file",validate:[{errorMsg:"只能为图片文件",regex:"\\.(png|jpe?g|gif|svg)(\\?.*)?$"}],maxSize:5e4,required:!1}],action:{save:{label:"注册"},others:[{key:"login",label:"返回登录!"}]}}}},save:function(r){var t=(r.key,r.data),a=(r.multipart,{headers:{"Content-Type":"multipart/form-data"},url:i.a.registration,method:"post",data:this.$Vue.generateFormData(t)}),n=this;o.a.request(a).then(function(r){r.data&&r.data.error?e.store.commit("FORM_SAVE_SUCCESS",{id:"registration-form",data:r.data}):n.succeed=!n.succeed}).catch(function(r){e.store.commit("FORM_SAVE_FAILURE",{id:"registration-form",error:r})})}.bind(this),ruleChange:function(e){if(void 0!==e.changed.repassword){for(var r in e.items)if("password"===e.items[r].name)return e.items[r].defaultValue!==e.changed.repassword?[{name:"repassword",validatedMsg:"密码不一致"}]:[{name:"repassword"}]}else if(void 0!==e.changed.password)for(var t in e.items)if("repassword"===e.items[t].name)return e.items[t].defaultValue!==e.changed.password?[{name:"repassword",validatedMsg:"密码不一致"}]:[{name:"repassword"}]}},formActionUrls:{login:i.a.loginPage}}},methods:{routeToLogin:function(){this.$router.push(i.a.loginPage)}},components:{panel:l,vform:c,modalTpl:d}}}.call(r,t(2))},185:function(e,r,t){"use strict";Object.defineProperty(r,"__esModule",{value:!0});var a=t(1),o=t.n(a),n=o.a.panel;r.default={components:{panel:n}}},252:function(e,r){},274:function(e,r){},279:function(e,r){},304:function(e,r,t){t(252);var a=t(0)(t(159),t(348),null,null);e.exports=a.exports},305:function(e,r,t){t(274);var a=t(0)(t(160),t(371),"data-v-c9d17500",null);e.exports=a.exports},348:function(e,r){e.exports={render:function(){var e=this,r=e.$createElement,t=e._self._c||r;return t("panel",{attrs:{id:"login-wrapper",col:6}},[t("h1",{slot:"header"},[e._v("用户登录")]),e._v(" "),e.authenticationError?t("p",{staticClass:"error"},[e._v("\n    用户名或密码错误,请重新输入\n  ")]):e._e(),e._v(" "),t("vform",{attrs:{id:"login-form",actionUrls:e.formActionUrls,actions:e.formActions}})],1)},staticRenderFns:[]}},371:function(e,r){e.exports={render:function(){var e=this,r=e.$createElement,t=e._self._c||r;return t("panel",{attrs:{id:"registration-wrapper",col:6}},[t("h1",{slot:"header"},[e._v("用户注册")]),e._v(" "),t("vform",{attrs:{id:"registration-form",actionUrls:e.formActionUrls,actions:e.formActions}}),e._v(" "),t("modalTpl",{attrs:{confirmModal:!1,type:"success",trigger:e.succeed},on:{closeEvent:e.routeToLogin}},[t("span",{slot:"header"},[e._v("注册成功")]),e._v(" "),t("div",{slot:"body"},[e._v("点击返回登录页面")])])],1)},staticRenderFns:[]}},376:function(e,r){e.exports={render:function(){var e=this,r=e.$createElement;return(e._self._c||r)("router-view")},staticRenderFns:[]}},73:function(e,r,t){"use strict";t.d(r,"a",function(){return a});var a={login:"http://localhost:9091/login",registrationPage:"registration",registration:"http://localhost:9091/user/register",loginPage:"/"}},96:function(e,r,t){"use strict";var a=t(5),o=t(40),n=t(304),s=t.n(n),i=t(305),l=t.n(i);a.default.use(o.a),r.a=new o.a({routes:[{path:"/",name:"Login",component:s.a},{path:"/registration",name:"Registration",component:l.a},{path:"/*",name:"All",component:s.a}]})}},[135]);
//# sourceMappingURL=login.7da2220170e3497e61b9.js.map