webpackJsonp([4],{

/***/ 115:
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
Object.defineProperty(__webpack_exports__, "__esModule", { value: true });
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_0_vue__ = __webpack_require__(6);
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_1__App__ = __webpack_require__(83);
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_1__App___default = __webpack_require__.n(__WEBPACK_IMPORTED_MODULE_1__App__);



new __WEBPACK_IMPORTED_MODULE_0_vue__["default"]({
  el: '#app',
  template: '<App/>',
  components: { App: __WEBPACK_IMPORTED_MODULE_1__App___default.a }
});

/***/ }),

/***/ 147:
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
Object.defineProperty(__webpack_exports__, "__esModule", { value: true });
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_0_axios__ = __webpack_require__(53);
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_0_axios___default = __webpack_require__.n(__WEBPACK_IMPORTED_MODULE_0_axios__);



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
/* harmony default export */ __webpack_exports__["default"] = ({
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
    }
  }
});

/***/ }),

/***/ 194:
/***/ (function(module, exports) {

// removed by extract-text-webpack-plugin

/***/ }),

/***/ 256:
/***/ (function(module, exports) {

module.exports={render:function (){var _vm=this;var _h=_vm.$createElement;var _c=_vm._self._c||_h;
  return _c('div', [_c('button', {
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
  }, [_vm._v("访问其他资源库的限制的资源")])])
},staticRenderFns: []}

/***/ }),

/***/ 83:
/***/ (function(module, exports, __webpack_require__) {


/* styles */
__webpack_require__(194)

var Component = __webpack_require__(0)(
  /* script */
  __webpack_require__(147),
  /* template */
  __webpack_require__(256),
  /* scopeId */
  "data-v-16249c88",
  /* cssModules */
  null
)

module.exports = Component.exports


/***/ })

},[115]);
//# sourceMappingURL=test.3d24c55127e763a9562c.js.map