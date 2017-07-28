webpackJsonp([5],{

/***/ 103:
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
/* harmony export (binding) */ __webpack_require__.d(__webpack_exports__, "a", function() { return commonUrls; });

var basePath = '';
var commonUrls = {
  login: basePath + 'login'
};



/***/ }),

/***/ 116:
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
Object.defineProperty(__webpack_exports__, "__esModule", { value: true });
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_0_vue__ = __webpack_require__(2);
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_1_vuex__ = __webpack_require__(9);
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_2__App__ = __webpack_require__(82);
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_2__App___default = __webpack_require__.n(__WEBPACK_IMPORTED_MODULE_2__App__);
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_3_huodh_vue_plugins__ = __webpack_require__(1);
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_3_huodh_vue_plugins___default = __webpack_require__.n(__WEBPACK_IMPORTED_MODULE_3_huodh_vue_plugins__);




__WEBPACK_IMPORTED_MODULE_0_vue__["default"].use(__WEBPACK_IMPORTED_MODULE_1_vuex__["default"]);
__WEBPACK_IMPORTED_MODULE_0_vue__["default"].use(__WEBPACK_IMPORTED_MODULE_3_huodh_vue_plugins___default.a.config);
var store = __WEBPACK_IMPORTED_MODULE_3_huodh_vue_plugins___default.a.generateStore();

new __WEBPACK_IMPORTED_MODULE_0_vue__["default"]({
  el: '#app',
  store: store,
  template: '<App/>',
  components: { App: __WEBPACK_IMPORTED_MODULE_2__App___default.a }
});

/***/ }),

/***/ 149:
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
Object.defineProperty(__webpack_exports__, "__esModule", { value: true });
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_0_huodh_vue_plugins__ = __webpack_require__(1);
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_0_huodh_vue_plugins___default = __webpack_require__.n(__WEBPACK_IMPORTED_MODULE_0_huodh_vue_plugins__);
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_1__components_common_loginOthers__ = __webpack_require__(103);





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
                }
              }
            }
          };
        }
      },
      formActionUrls: {
        directSaveUrl: __WEBPACK_IMPORTED_MODULE_1__components_common_loginOthers__["a" /* commonUrls */].login
      },
      authenticationError: false
    };
  },

  methods: {},
  components: {
    panel: panel, vform: vform
  },
  mounted: function mounted() {
    var regex = new RegExp('[?&]' + name + '(=([^&#]*)|&|#|$)');
    var results = regex.exec(window.location.href);
    if (results && results[2]) {
      this.authenticationError = true;
    }
  }
});

/***/ }),

/***/ 200:
/***/ (function(module, exports) {

// removed by extract-text-webpack-plugin

/***/ }),

/***/ 263:
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

/***/ 82:
/***/ (function(module, exports, __webpack_require__) {


/* styles */
__webpack_require__(200)

var Component = __webpack_require__(0)(
  /* script */
  __webpack_require__(149),
  /* template */
  __webpack_require__(263),
  /* scopeId */
  null,
  /* cssModules */
  null
)

module.exports = Component.exports


/***/ })

},[116]);
//# sourceMappingURL=loginOthers.f725effcdb32de58441b.js.map