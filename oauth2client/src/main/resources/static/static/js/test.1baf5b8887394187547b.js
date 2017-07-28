webpackJsonp([4],{

/***/ 118:
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
Object.defineProperty(__webpack_exports__, "__esModule", { value: true });
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_0_vue__ = __webpack_require__(2);
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_1__App__ = __webpack_require__(84);
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_1__App___default = __webpack_require__.n(__WEBPACK_IMPORTED_MODULE_1__App__);



new __WEBPACK_IMPORTED_MODULE_0_vue__["default"]({
  el: '#app',
  template: '<App/>',
  components: { App: __WEBPACK_IMPORTED_MODULE_1__App___default.a }
});

/***/ }),

/***/ 151:
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
Object.defineProperty(__webpack_exports__, "__esModule", { value: true });
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_0_axios__ = __webpack_require__(53);
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_0_axios___default = __webpack_require__.n(__WEBPACK_IMPORTED_MODULE_0_axios__);
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_1__components_common_Head__ = __webpack_require__(27);
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_1__components_common_Head___default = __webpack_require__.n(__WEBPACK_IMPORTED_MODULE_1__components_common_Head__);
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_2__components_common_Foot__ = __webpack_require__(26);
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_2__components_common_Foot___default = __webpack_require__.n(__WEBPACK_IMPORTED_MODULE_2__components_common_Foot__);





var v = window.document.cookie.match('(^|;) ?accesstoken=([^;]*)(;|$)');
var accesstoken = v ? v[2] : null;
var config1 = {
  headers: { 'Authorization': 'Bearer ' + accesstoken },
  url: 'testresource',
  method: 'get'
};
var config2 = {
  headers: { 'Authorization': 'Bearer ' + accesstoken },
  url: 'http://localhost:9091/resources/test/testresource',
  method: 'get',
  withCredentials: true
};
var config3 = {
  headers: { 'Authorization': 'Bearer ' + accesstoken },
  url: 'http://localhost:9091/resources/test/testclientresource',
  method: 'get',
  withCredentials: true
};
/* harmony default export */ __webpack_exports__["default"] = ({
  components: {
    vhead: __WEBPACK_IMPORTED_MODULE_1__components_common_Head___default.a,
    vfoot: __WEBPACK_IMPORTED_MODULE_2__components_common_Foot___default.a
  },
  methods: {
    getRestrictedResourceInSameClient: function getRestrictedResourceInSameClient() {
      __WEBPACK_IMPORTED_MODULE_0_axios___default.a.request(config1).then(function (response) {
        console.log('success:' + response.data.message);
      }).catch(function (error) {
        console.log('error:' + error);
      });
    },
    getRestrictedResourceInDifferentClient: function getRestrictedResourceInDifferentClient() {
      __WEBPACK_IMPORTED_MODULE_0_axios___default.a.request(config2).then(function (response) {
        console.log('success2:' + response.data.message);
      }).catch(function (error) {
        console.log('error2:' + error);
      });
    },
    getResourceInDifferentClient: function getResourceInDifferentClient() {
      __WEBPACK_IMPORTED_MODULE_0_axios___default.a.request(config3).then(function (response) {
        console.log('success:' + response.data.message);
      }).catch(function (error) {
        console.log('error:' + error);
      });
    }
  }
});

/***/ }),

/***/ 16:
/***/ (function(module, exports) {



/***/ }),

/***/ 17:
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
Object.defineProperty(__webpack_exports__, "__esModule", { value: true });


/* harmony default export */ __webpack_exports__["default"] = ({
  props: {
    appName: {
      default: 'Hopever'
    }
  }
});

/***/ }),

/***/ 198:
/***/ (function(module, exports) {

// removed by extract-text-webpack-plugin

/***/ }),

/***/ 24:
/***/ (function(module, exports) {

// removed by extract-text-webpack-plugin

/***/ }),

/***/ 25:
/***/ (function(module, exports) {

// removed by extract-text-webpack-plugin

/***/ }),

/***/ 26:
/***/ (function(module, exports, __webpack_require__) {


/* styles */
__webpack_require__(24)

var Component = __webpack_require__(0)(
  /* script */
  __webpack_require__(16),
  /* template */
  __webpack_require__(28),
  /* scopeId */
  "data-v-2bba4569",
  /* cssModules */
  null
)

module.exports = Component.exports


/***/ }),

/***/ 261:
/***/ (function(module, exports) {

module.exports={render:function (){var _vm=this;var _h=_vm.$createElement;var _c=_vm._self._c||_h;
  return _c('div', [_c('vhead', {
    attrs: {
      "appName": "oauth2测试"
    }
  }), _vm._v(" "), _vm._m(0), _vm._v(" "), _c('div', [_c('button', {
    on: {
      "click": function($event) {
        $event.preventDefault();
        _vm.getRestrictedResourceInSameClient($event)
      }
    }
  }, [_vm._v("访问client同app的限制资源")]), _vm._v(" "), _c('button', {
    on: {
      "click": function($event) {
        $event.preventDefault();
        _vm.getRestrictedResourceInDifferentClient($event)
      }
    }
  }, [_vm._v("访问其他资源库的限制的资源")]), _vm._v(" "), _c('button', {
    on: {
      "click": function($event) {
        $event.preventDefault();
        _vm.getResourceInDifferentClient($event)
      }
    }
  }, [_vm._v("访问其他资源库的client可用资源")])]), _vm._v(" "), _c('vfoot')], 1)
},staticRenderFns: [function (){var _vm=this;var _h=_vm.$createElement;var _c=_vm._self._c||_h;
  return _c('div', {
    staticStyle: {
      "margin": "20px 0"
    }
  }, [_c('a', {
    staticClass: "btn btn-primary",
    attrs: {
      "href": "gettokenbycode"
    }
  }, [_vm._v("使用authcode方式获取资源")]), _vm._v(" "), _c('a', {
    staticClass: "btn btn-primary",
    attrs: {
      "href": "gettokenbyclient"
    }
  }, [_vm._v("使用client方式获取资源")])])
}]}

/***/ }),

/***/ 27:
/***/ (function(module, exports, __webpack_require__) {


/* styles */
__webpack_require__(25)

var Component = __webpack_require__(0)(
  /* script */
  __webpack_require__(17),
  /* template */
  __webpack_require__(29),
  /* scopeId */
  "data-v-5c8e044a",
  /* cssModules */
  null
)

module.exports = Component.exports


/***/ }),

/***/ 28:
/***/ (function(module, exports) {

module.exports={render:function (){var _vm=this;var _h=_vm.$createElement;var _c=_vm._self._c||_h;
  return _vm._m(0)
},staticRenderFns: [function (){var _vm=this;var _h=_vm.$createElement;var _c=_vm._self._c||_h;
  return _c('div', {
    staticClass: "container-fluid footer"
  }, [_c('div', {
    staticClass: "pull-left"
  }, [_c('a', [_vm._v("Legal notice")]), _c('a', [_vm._v("Privacy policy")])]), _vm._v(" "), _c('div', {
    staticClass: "pull-right"
  }, [_vm._v("©2017 Hopever – All right reserved")])])
}]}

/***/ }),

/***/ 29:
/***/ (function(module, exports) {

module.exports={render:function (){var _vm=this;var _h=_vm.$createElement;var _c=_vm._self._c||_h;
  return _c('div', {
    staticClass: "container-fluid head"
  }, [_c('div', {
    staticClass: "pull-left"
  }, [_c('h3', [_c('span', {
    staticClass: "brand"
  }, [_vm._v(_vm._s(_vm.appName))]), _vm._v("管理平台")])]), _vm._v(" "), _vm._m(0)])
},staticRenderFns: [function (){var _vm=this;var _h=_vm.$createElement;var _c=_vm._self._c||_h;
  return _c('div', {
    staticClass: "pull-right"
  }, [_c('a', {
    attrs: {
      "href": "logout",
      "title": "logout"
    }
  }, [_vm._v("Logout"), _c('span', {
    staticClass: "glyphicon glyphicon-menu-right"
  })])])
}]}

/***/ }),

/***/ 84:
/***/ (function(module, exports, __webpack_require__) {


/* styles */
__webpack_require__(198)

var Component = __webpack_require__(0)(
  /* script */
  __webpack_require__(151),
  /* template */
  __webpack_require__(261),
  /* scopeId */
  "data-v-16249c88",
  /* cssModules */
  null
)

module.exports = Component.exports


/***/ })

},[118]);
//# sourceMappingURL=test.1baf5b8887394187547b.js.map