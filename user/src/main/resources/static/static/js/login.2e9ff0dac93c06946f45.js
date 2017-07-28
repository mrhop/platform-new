webpackJsonp([3],{

/***/ 113:
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
Object.defineProperty(__webpack_exports__, "__esModule", { value: true });
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_0_vue__ = __webpack_require__(7);
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_1__router_login__ = __webpack_require__(78);
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_2_vuex__ = __webpack_require__(15);
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_3__App__ = __webpack_require__(81);
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_3__App___default = __webpack_require__.n(__WEBPACK_IMPORTED_MODULE_3__App__);
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_4_huodh_vue_plugins__ = __webpack_require__(1);
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_4_huodh_vue_plugins___default = __webpack_require__.n(__WEBPACK_IMPORTED_MODULE_4_huodh_vue_plugins__);





__WEBPACK_IMPORTED_MODULE_0_vue__["default"].use(__WEBPACK_IMPORTED_MODULE_2_vuex__["default"]);
__WEBPACK_IMPORTED_MODULE_0_vue__["default"].use(__WEBPACK_IMPORTED_MODULE_4_huodh_vue_plugins___default.a.config);
var store = __WEBPACK_IMPORTED_MODULE_4_huodh_vue_plugins___default.a.generateStore();

new __WEBPACK_IMPORTED_MODULE_0_vue__["default"]({
  el: '#app',
  router: __WEBPACK_IMPORTED_MODULE_1__router_login__["a" /* default */],
  store: store,
  template: '<App/>',
  components: { App: __WEBPACK_IMPORTED_MODULE_3__App___default.a }
});

/***/ }),

/***/ 136:
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
Object.defineProperty(__webpack_exports__, "__esModule", { value: true });
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_0_huodh_vue_plugins__ = __webpack_require__(1);
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_0_huodh_vue_plugins___default = __webpack_require__.n(__WEBPACK_IMPORTED_MODULE_0_huodh_vue_plugins__);
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_1__common_login__ = __webpack_require__(58);





var panel = __WEBPACK_IMPORTED_MODULE_0_huodh_vue_plugins___default.a.panel;
var vform = __WEBPACK_IMPORTED_MODULE_0_huodh_vue_plugins___default.a.vform;

/* harmony default export */ __webpack_exports__["default"] = ({
  data: function data() {
    return {
      formActions: {
        init: function init(params) {
          return {
            'rules': {
              'items': [{
                'name': 'username',
                'label': '账号',
                'type': 'text',
                'validate': [{
                  'errorMsg': '不能为空',
                  'regex': '^\\S+$'
                }, {
                  'errorMsg': '请输入正确的账号，手机号或者Email',
                  'regex': '^(\\w{5,40})|(\\w+([-+.]\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*)|((13[0-9]|14[5|7]|15[0|1|2|3|5|6|7|8|9]|(17|18)[0|1|2|3|5|6|7|8|9])\\d{8})$'
                }],
                'placeholder': '账号/手机号/Email',
                'locked': false,
                'error': true
              }, {
                'name': 'password',
                'label': '密码',
                'type': 'password',
                'placeholder': '密码',
                'validate': [{
                  'errorMsg': '不能为空',
                  'regex': '^\\S+$'
                }]
              }],
              'action': {
                'save': {
                  'label': '登录'
                },
                'others': [{
                  'key': 'registration',
                  'label': '没有账号？请注册!'
                }]
              }
            }
          };
        }
      },
      formActionUrls: {
        directSaveUrl: __WEBPACK_IMPORTED_MODULE_1__common_login__["a" /* commonUrls */].login,
        registration: __WEBPACK_IMPORTED_MODULE_1__common_login__["a" /* commonUrls */].registrationPage
      },
      authenticationError: false
    };
  },

  methods: {},
  components: {
    panel: panel, vform: vform
  },
  mounted: function mounted() {
    if (this.$route.query.authentication_error) {
      this.authenticationError = true;
    }
  }
});

/***/ }),

/***/ 137:
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
Object.defineProperty(__webpack_exports__, "__esModule", { value: true });
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_0_babel_runtime_core_js_json_stringify__ = __webpack_require__(23);
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_0_babel_runtime_core_js_json_stringify___default = __webpack_require__.n(__WEBPACK_IMPORTED_MODULE_0_babel_runtime_core_js_json_stringify__);
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_1_huodh_vue_plugins__ = __webpack_require__(1);
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_1_huodh_vue_plugins___default = __webpack_require__.n(__WEBPACK_IMPORTED_MODULE_1_huodh_vue_plugins__);
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_2__common_login__ = __webpack_require__(58);






var panel = __WEBPACK_IMPORTED_MODULE_1_huodh_vue_plugins___default.a.panel;
var vform = __WEBPACK_IMPORTED_MODULE_1_huodh_vue_plugins___default.a.vform;
/* harmony default export */ __webpack_exports__["default"] = ({
  data: function data() {
    return {
      formActions: {
        init: function init(params) {
          return {
            'rules': {
              'items': [{
                'name': 'account',
                'label': '账号',
                'type': 'text',
                'validate': [{
                  'errorMsg': '不能为空',
                  'regex': '^\\S+$'
                }, {
                  'errorMsg': '账号由英文，数字和 _ 组成，并在5-40个字符之间',
                  'regex': '^\\w{5,40}$'
                }],
                'placeholder': '账号',
                'locked': false,
                'error': true
              }, {
                'name': 'phone',
                'label': '手机号',
                'type': 'text',
                'validate': [{
                  'errorMsg': '不能为空',
                  'regex': '^\\S+$'
                }, {
                  'errorMsg': '请输入正确的手机号',
                  'regex': '^(13[0-9]|14[5|7]|15[0|1|2|3|5|6|7|8|9]|(17|18)[0|1|2|3|5|6|7|8|9])\\d{8}$'
                }],
                'placeholder': '手机号',
                'locked': false,
                'error': true
              }, {
                'name': 'email',
                'label': '邮箱',
                'type': 'text',
                'validate': [{
                  'errorMsg': '不能为空',
                  'regex': '^\\S+$'
                }, {
                  'errorMsg': '请输入正确的Email',
                  'regex': '^\\w+([-+.]\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*$'
                }],
                'placeholder': 'Email',
                'locked': false,
                'error': true
              }, {
                'name': 'password',
                'label': '密码',
                'type': 'password',
                'placeholder': '密码',
                'validate': [{
                  'errorMsg': '不能为空',
                  'regex': '^\\S+$'
                }, {
                  'errorMsg': '至少包含数字，字母以及特殊字符【!@#$%^&*_】中任意两种,并在5-15字符之间',
                  'regex': '^(?![a-zA-Z]+$)(?!\\d+$)(?![!@#$%^&*_]+$)[\\w!@#$%^&*]{5,15}$'
                }]
              }, {
                'name': 'repeatPassword',
                'label': '确认密码',
                'type': 'password',
                'validate': [{
                  'errorMsg': '不能为空',
                  'regex': '^\\S+$'
                }]
              }],
              'action': {
                'save': {
                  'label': '注册'
                },
                'others': [{
                  'key': 'login',
                  'label': '返回登录!'
                }]
              }
            }
          };
        },
        save: function save(params) {
          if (params.data) {
            console.log('these data will be saved:' + __WEBPACK_IMPORTED_MODULE_0_babel_runtime_core_js_json_stringify___default()(params.data));
          }

          console.log('deal with the data by this save function itself');
          return {
            success: {
              title: '保存数据',
              message: '保存成功'
            }
          };
        }
      },
      formActionUrls: {
        login: __WEBPACK_IMPORTED_MODULE_2__common_login__["a" /* commonUrls */].loginPage
      }
    };
  },

  methods: {},
  components: {
    panel: panel, vform: vform
  }
});

/***/ }),

/***/ 144:
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
Object.defineProperty(__webpack_exports__, "__esModule", { value: true });
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_0_huodh_vue_plugins__ = __webpack_require__(1);
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_0_huodh_vue_plugins___default = __webpack_require__.n(__WEBPACK_IMPORTED_MODULE_0_huodh_vue_plugins__);



var panel = __WEBPACK_IMPORTED_MODULE_0_huodh_vue_plugins___default.a.panel;
/* harmony default export */ __webpack_exports__["default"] = ({
  components: {
    panel: panel
  }
});

/***/ }),

/***/ 200:
/***/ (function(module, exports) {

// removed by extract-text-webpack-plugin

/***/ }),

/***/ 214:
/***/ (function(module, exports) {

// removed by extract-text-webpack-plugin

/***/ }),

/***/ 218:
/***/ (function(module, exports) {

// removed by extract-text-webpack-plugin

/***/ }),

/***/ 243:
/***/ (function(module, exports, __webpack_require__) {


/* styles */
__webpack_require__(200)

var Component = __webpack_require__(0)(
  /* script */
  __webpack_require__(136),
  /* template */
  __webpack_require__(261),
  /* scopeId */
  null,
  /* cssModules */
  null
)

module.exports = Component.exports


/***/ }),

/***/ 244:
/***/ (function(module, exports, __webpack_require__) {


/* styles */
__webpack_require__(214)

var Component = __webpack_require__(0)(
  /* script */
  __webpack_require__(137),
  /* template */
  __webpack_require__(276),
  /* scopeId */
  "data-v-c9d17500",
  /* cssModules */
  null
)

module.exports = Component.exports


/***/ }),

/***/ 261:
/***/ (function(module, exports) {

module.exports={render:function (){var _vm=this;var _h=_vm.$createElement;var _c=_vm._self._c||_h;
  return _c('panel', {
    attrs: {
      "id": "login-wrapper",
      "col": 6
    }
  }, [_c('h1', {
    slot: "header"
  }, [_vm._v("用户登录")]), _vm._v(" "), (_vm.authenticationError) ? _c('p', {
    staticClass: "error"
  }, [_vm._v("\n    用户名或密码错误,请重新输入\n  ")]) : _vm._e(), _vm._v(" "), _c('vform', {
    attrs: {
      "id": "login-form",
      "actionUrls": _vm.formActionUrls,
      "actions": _vm.formActions
    }
  })], 1)
},staticRenderFns: []}

/***/ }),

/***/ 276:
/***/ (function(module, exports) {

module.exports={render:function (){var _vm=this;var _h=_vm.$createElement;var _c=_vm._self._c||_h;
  return _c('panel', {
    attrs: {
      "id": "registration-wrapper",
      "col": 6
    }
  }, [_c('h1', {
    slot: "header"
  }, [_vm._v("用户注册")]), _vm._v(" "), _c('vform', {
    attrs: {
      "id": "registration-form",
      "actionUrls": _vm.formActionUrls,
      "actions": _vm.formActions
    }
  })], 1)
},staticRenderFns: []}

/***/ }),

/***/ 280:
/***/ (function(module, exports) {

module.exports={render:function (){var _vm=this;var _h=_vm.$createElement;var _c=_vm._self._c||_h;
  return _c('router-view')
},staticRenderFns: []}

/***/ }),

/***/ 58:
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
/* WEBPACK VAR INJECTION */(function(global) {/* harmony export (binding) */ __webpack_require__.d(__webpack_exports__, "a", function() { return commonUrls; });

global.basePath = '';
var commonUrls = {
  login: global.basePath + 'login',
  registrationPage: 'registration',
  registration: global.basePath + 'data/registration.html',
  loginPage: '/'
};


/* WEBPACK VAR INJECTION */}.call(__webpack_exports__, __webpack_require__(16)))

/***/ }),

/***/ 78:
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_0_vue__ = __webpack_require__(7);
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_1_vue_router__ = __webpack_require__(52);
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_2__components_login_login_Login__ = __webpack_require__(243);
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_2__components_login_login_Login___default = __webpack_require__.n(__WEBPACK_IMPORTED_MODULE_2__components_login_login_Login__);
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_3__components_login_registration_Registration__ = __webpack_require__(244);
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_3__components_login_registration_Registration___default = __webpack_require__.n(__WEBPACK_IMPORTED_MODULE_3__components_login_registration_Registration__);





__WEBPACK_IMPORTED_MODULE_0_vue__["default"].use(__WEBPACK_IMPORTED_MODULE_1_vue_router__["a" /* default */]);

/* harmony default export */ __webpack_exports__["a"] = (new __WEBPACK_IMPORTED_MODULE_1_vue_router__["a" /* default */]({
  routes: [{
    path: '/',
    name: 'Login',
    component: __WEBPACK_IMPORTED_MODULE_2__components_login_login_Login___default.a
  }, {
    path: '/registration',
    name: 'Registration',
    component: __WEBPACK_IMPORTED_MODULE_3__components_login_registration_Registration___default.a
  }]
}));

/***/ }),

/***/ 81:
/***/ (function(module, exports, __webpack_require__) {


/* styles */
__webpack_require__(218)

var Component = __webpack_require__(0)(
  /* script */
  __webpack_require__(144),
  /* template */
  __webpack_require__(280),
  /* scopeId */
  null,
  /* cssModules */
  null
)

module.exports = Component.exports


/***/ })

},[113]);
//# sourceMappingURL=login.2e9ff0dac93c06946f45.js.map