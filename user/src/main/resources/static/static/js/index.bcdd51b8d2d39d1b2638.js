webpackJsonp([1],[
/* 0 */,
/* 1 */,
/* 2 */,
/* 3 */,
/* 4 */,
/* 5 */,
/* 6 */,
/* 7 */,
/* 8 */,
/* 9 */,
/* 10 */
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
/* WEBPACK VAR INJECTION */(function(global) {/* harmony export (binding) */ __webpack_require__.d(__webpack_exports__, "a", function() { return commonUrls; });

global.basePath = '';
var commonUrls = {
  leftTree: global.basePath + 'static/demo-data/tree.json',
  leftTreeDemo: global.basePath + 'static/demo-data/tree/tree.json',
  testTreeWithTableInit: global.basePath + 'static/demo-data/treeWithTable/tree.json',
  testEditTreeInit: global.basePath + 'static/demo-data/editTree/tree.json',
  testEditTreeSave: global.basePath + 'data/tree-item-save.html',
  testEditTreeDelete: global.basePath + 'data/tree-item-delete.html',
  testEditTreeUpdate: global.basePath + 'data/tree-item-update.html',
  testTableInit: global.basePath + 'data/table.html',
  testTableForTreeInit: global.basePath + 'data/tablefortree.html',
  testTableRowDel: global.basePath + 'data/table-delete.html',
  testFormInit: global.basePath + 'data/form-init.html',
  testFormReset: global.basePath + 'data/form-reset.html',
  testFormSave: global.basePath + 'data/form-save.html',
  testFormRuleChange: global.basePath + 'data/form-rulechange.html',
  vuerouter: {
    testTable: 'table',
    testForm: 'form'
  }
};


/* WEBPACK VAR INJECTION */}.call(__webpack_exports__, __webpack_require__(16)))

/***/ }),
/* 11 */,
/* 12 */,
/* 13 */,
/* 14 */,
/* 15 */,
/* 16 */,
/* 17 */,
/* 18 */,
/* 19 */,
/* 20 */,
/* 21 */,
/* 22 */,
/* 23 */,
/* 24 */,
/* 25 */,
/* 26 */,
/* 27 */,
/* 28 */,
/* 29 */,
/* 30 */
/***/ (function(module, exports) {



/***/ }),
/* 31 */
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
/* 32 */,
/* 33 */,
/* 34 */,
/* 35 */,
/* 36 */,
/* 37 */,
/* 38 */,
/* 39 */,
/* 40 */,
/* 41 */,
/* 42 */,
/* 43 */,
/* 44 */,
/* 45 */
/***/ (function(module, exports) {

// removed by extract-text-webpack-plugin

/***/ }),
/* 46 */
/***/ (function(module, exports) {

// removed by extract-text-webpack-plugin

/***/ }),
/* 47 */,
/* 48 */
/***/ (function(module, exports, __webpack_require__) {


/* styles */
__webpack_require__(45)

var Component = __webpack_require__(0)(
  /* script */
  __webpack_require__(30),
  /* template */
  __webpack_require__(50),
  /* scopeId */
  "data-v-2bba4569",
  /* cssModules */
  null
)

module.exports = Component.exports


/***/ }),
/* 49 */
/***/ (function(module, exports, __webpack_require__) {


/* styles */
__webpack_require__(46)

var Component = __webpack_require__(0)(
  /* script */
  __webpack_require__(31),
  /* template */
  __webpack_require__(51),
  /* scopeId */
  "data-v-5c8e044a",
  /* cssModules */
  null
)

module.exports = Component.exports


/***/ }),
/* 50 */
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
/* 51 */
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
/* 52 */,
/* 53 */,
/* 54 */,
/* 55 */,
/* 56 */,
/* 57 */,
/* 58 */,
/* 59 */,
/* 60 */,
/* 61 */,
/* 62 */,
/* 63 */,
/* 64 */,
/* 65 */,
/* 66 */,
/* 67 */,
/* 68 */,
/* 69 */,
/* 70 */,
/* 71 */,
/* 72 */,
/* 73 */,
/* 74 */,
/* 75 */,
/* 76 */,
/* 77 */
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_0_vue__ = __webpack_require__(7);
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_1_vue_router__ = __webpack_require__(52);
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_2__components_index_dashboard_Dashboard__ = __webpack_require__(225);
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_2__components_index_dashboard_Dashboard___default = __webpack_require__.n(__WEBPACK_IMPORTED_MODULE_2__components_index_dashboard_Dashboard__);
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_3__components_index_panel_Panel__ = __webpack_require__(237);
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_3__components_index_panel_Panel___default = __webpack_require__.n(__WEBPACK_IMPORTED_MODULE_3__components_index_panel_Panel__);
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_4__components_index_tab_Tab__ = __webpack_require__(238);
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_4__components_index_tab_Tab___default = __webpack_require__.n(__WEBPACK_IMPORTED_MODULE_4__components_index_tab_Tab__);
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_5__components_index_modal_Modal__ = __webpack_require__(233);
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_5__components_index_modal_Modal___default = __webpack_require__.n(__WEBPACK_IMPORTED_MODULE_5__components_index_modal_Modal__);
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_6__components_index_table_Table__ = __webpack_require__(240);
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_6__components_index_table_Table___default = __webpack_require__.n(__WEBPACK_IMPORTED_MODULE_6__components_index_table_Table__);
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_7__components_index_form_Form__ = __webpack_require__(228);
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_7__components_index_form_Form___default = __webpack_require__.n(__WEBPACK_IMPORTED_MODULE_7__components_index_form_Form__);
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_8__components_index_tree_Tree__ = __webpack_require__(242);
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_8__components_index_tree_Tree___default = __webpack_require__.n(__WEBPACK_IMPORTED_MODULE_8__components_index_tree_Tree__);
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_9__components_index_date_Date__ = __webpack_require__(226);
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_9__components_index_date_Date___default = __webpack_require__.n(__WEBPACK_IMPORTED_MODULE_9__components_index_date_Date__);
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_10__components_index_treeWithTable_TreeWithTable__ = __webpack_require__(241);
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_10__components_index_treeWithTable_TreeWithTable___default = __webpack_require__.n(__WEBPACK_IMPORTED_MODULE_10__components_index_treeWithTable_TreeWithTable__);
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_11__components_index_editTree_EditTree__ = __webpack_require__(227);
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_11__components_index_editTree_EditTree___default = __webpack_require__.n(__WEBPACK_IMPORTED_MODULE_11__components_index_editTree_EditTree__);
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_12__components_index_actionForm_ActionForm__ = __webpack_require__(222);
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_12__components_index_actionForm_ActionForm___default = __webpack_require__.n(__WEBPACK_IMPORTED_MODULE_12__components_index_actionForm_ActionForm__);
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_13__components_index_actionTable_ActionTable__ = __webpack_require__(223);
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_13__components_index_actionTable_ActionTable___default = __webpack_require__.n(__WEBPACK_IMPORTED_MODULE_13__components_index_actionTable_ActionTable__);
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_14__components_index_actionTree_ActionTree__ = __webpack_require__(224);
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_14__components_index_actionTree_ActionTree___default = __webpack_require__.n(__WEBPACK_IMPORTED_MODULE_14__components_index_actionTree_ActionTree__);
















__WEBPACK_IMPORTED_MODULE_0_vue__["default"].use(__WEBPACK_IMPORTED_MODULE_1_vue_router__["a" /* default */]);

/* harmony default export */ __webpack_exports__["a"] = (new __WEBPACK_IMPORTED_MODULE_1_vue_router__["a" /* default */]({
  routes: [{
    path: '/',
    name: 'Dashboard',
    component: __WEBPACK_IMPORTED_MODULE_2__components_index_dashboard_Dashboard___default.a
  }, {
    path: '/panel',
    name: 'Panel',
    component: __WEBPACK_IMPORTED_MODULE_3__components_index_panel_Panel___default.a
  }, {
    path: '/tab',
    name: 'Tab',
    component: __WEBPACK_IMPORTED_MODULE_4__components_index_tab_Tab___default.a
  }, {
    path: '/modal',
    name: 'Modal',
    component: __WEBPACK_IMPORTED_MODULE_5__components_index_modal_Modal___default.a
  }, {
    path: '/table',
    name: 'Table',
    component: __WEBPACK_IMPORTED_MODULE_6__components_index_table_Table___default.a
  }, {
    path: '/form',
    name: 'Form',
    component: __WEBPACK_IMPORTED_MODULE_7__components_index_form_Form___default.a
  }, {
    path: '/tree',
    name: 'Tree',
    component: __WEBPACK_IMPORTED_MODULE_8__components_index_tree_Tree___default.a
  }, {
    path: '/date',
    name: 'Date',
    component: __WEBPACK_IMPORTED_MODULE_9__components_index_date_Date___default.a
  }, {
    path: '/treewithtable',
    name: 'TreeWithTable',
    component: __WEBPACK_IMPORTED_MODULE_10__components_index_treeWithTable_TreeWithTable___default.a
  }, {
    path: '/edittree',
    name: 'EditTree',
    component: __WEBPACK_IMPORTED_MODULE_11__components_index_editTree_EditTree___default.a
  }, {
    path: '/actionform',
    name: 'ActionForm',
    component: __WEBPACK_IMPORTED_MODULE_12__components_index_actionForm_ActionForm___default.a
  }, {
    path: '/actiontable',
    name: 'ActionTable',
    component: __WEBPACK_IMPORTED_MODULE_13__components_index_actionTable_ActionTable___default.a
  }, {
    path: '/actiontree',
    name: 'ActionTree',
    component: __WEBPACK_IMPORTED_MODULE_14__components_index_actionTree_ActionTree___default.a
  }]
}));

/***/ }),
/* 78 */,
/* 79 */,
/* 80 */
/***/ (function(module, exports, __webpack_require__) {


/* styles */
__webpack_require__(194)

var Component = __webpack_require__(0)(
  /* script */
  __webpack_require__(143),
  /* template */
  __webpack_require__(255),
  /* scopeId */
  "data-v-28c62488",
  /* cssModules */
  null
)

module.exports = Component.exports


/***/ }),
/* 81 */,
/* 82 */,
/* 83 */,
/* 84 */,
/* 85 */,
/* 86 */,
/* 87 */,
/* 88 */,
/* 89 */,
/* 90 */,
/* 91 */,
/* 92 */,
/* 93 */,
/* 94 */,
/* 95 */,
/* 96 */,
/* 97 */,
/* 98 */,
/* 99 */,
/* 100 */,
/* 101 */,
/* 102 */
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_0_prismjs__ = __webpack_require__(5);
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_0_prismjs___default = __webpack_require__.n(__WEBPACK_IMPORTED_MODULE_0_prismjs__);

/* harmony default export */ __webpack_exports__["a"] = ([{
  'title': 'form代码',
  'content': {
    template: '<pre :data-src="$basePath +\'static/demo-data/actionsComponents/actionForm/ActionForm.vue\'"></pre>'
  },
  'show': true
}, {
  'title': 'form数据格式',
  'content': {
    template: '<pre :data-src="$basePath +\'static/demo-data/form/form.json\'"></pre>'
  },
  'show': false
}, {
  'title': 'form中tree element数据',
  'content': {
    template: '<pre :data-src="$basePath +\'static/demo-data/form/tree.json\'"></pre>',
    mounted: function mounted() {
      __WEBPACK_IMPORTED_MODULE_0_prismjs___default.a.highlightAll();
      __WEBPACK_IMPORTED_MODULE_0_prismjs___default.a.fileHighlight();
    }
  },
  'show': false
}, {
  'title': '联动规则',
  'url': 'static/demo-data/form/linkage-desc.html',
  'show': false
}]);

/***/ }),
/* 103 */
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_0_prismjs__ = __webpack_require__(5);
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_0_prismjs___default = __webpack_require__.n(__WEBPACK_IMPORTED_MODULE_0_prismjs__);

/* harmony default export */ __webpack_exports__["a"] = ([{
  'title': 'table代码',
  'content': {
    template: '<pre :data-src="$basePath +\'static/demo-data/actionsComponents/actionTable/ActionTable.vue\'"></pre>'
  },
  'show': true
}, {
  'title': 'table数据格式',
  'content': {
    template: '<pre :data-src="$basePath +\'static/demo-data/table/table.json\'"></pre>',
    mounted: function mounted() {
      __WEBPACK_IMPORTED_MODULE_0_prismjs___default.a.highlightAll();
      __WEBPACK_IMPORTED_MODULE_0_prismjs___default.a.fileHighlight();
    }
  },
  'show': false
}]);

/***/ }),
/* 104 */
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_0_prismjs__ = __webpack_require__(5);
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_0_prismjs___default = __webpack_require__.n(__WEBPACK_IMPORTED_MODULE_0_prismjs__);

/* harmony default export */ __webpack_exports__["a"] = ([{
  'title': 'tree代码',
  'content': {
    template: '<pre :data-src="$basePath +\'static/demo-data/actionsComponents/actionTree/ActionTree.vue\'"></pre>'
  },
  'show': true
}, {
  'title': 'tree数据格式',
  'content': {
    template: '<pre :data-src="$basePath +\'static/demo-data/editTree/tree.json\'"></pre>',
    mounted: function mounted() {
      __WEBPACK_IMPORTED_MODULE_0_prismjs___default.a.highlightAll();
      __WEBPACK_IMPORTED_MODULE_0_prismjs___default.a.fileHighlight();
    }
  },
  'show': false
}]);

/***/ }),
/* 105 */
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_0_prismjs__ = __webpack_require__(5);
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_0_prismjs___default = __webpack_require__.n(__WEBPACK_IMPORTED_MODULE_0_prismjs__);

/* harmony default export */ __webpack_exports__["a"] = ([{
  'title': 'date代码',
  'content': {
    template: '<pre :data-src="$basePath +\'static/demo-data/date/Date.vue\'"></pre>',
    mounted: function mounted() {
      __WEBPACK_IMPORTED_MODULE_0_prismjs___default.a.highlightAll();
      __WEBPACK_IMPORTED_MODULE_0_prismjs___default.a.fileHighlight();
    }
  },
  'show': true
}]);

/***/ }),
/* 106 */
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_0_prismjs__ = __webpack_require__(5);
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_0_prismjs___default = __webpack_require__.n(__WEBPACK_IMPORTED_MODULE_0_prismjs__);

/* harmony default export */ __webpack_exports__["a"] = ([{
  'title': 'tree代码',
  'content': {
    template: '<pre :data-src="$basePath +\'static/demo-data/editTree/EditTree.vue\'"></pre>'
  },
  'show': true
}, {
  'title': 'tree数据格式',
  'content': {
    template: '<pre :data-src="$basePath +\'static/demo-data/editTree/tree.json\'"></pre>',
    mounted: function mounted() {
      __WEBPACK_IMPORTED_MODULE_0_prismjs___default.a.highlightAll();
      __WEBPACK_IMPORTED_MODULE_0_prismjs___default.a.fileHighlight();
    }
  },
  'show': false
}]);

/***/ }),
/* 107 */
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_0_prismjs__ = __webpack_require__(5);
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_0_prismjs___default = __webpack_require__.n(__WEBPACK_IMPORTED_MODULE_0_prismjs__);

/* harmony default export */ __webpack_exports__["a"] = ([{
  'title': 'form代码',
  'content': {
    template: '<pre :data-src="$basePath +\'static/demo-data/form/Form.vue\'"></pre>'
  },
  'show': true
}, {
  'title': 'form数据格式',
  'content': {
    template: '<pre :data-src="$basePath +\'static/demo-data/form/form.json\'"></pre>'
  },
  'show': false
}, {
  'title': 'form中tree element数据',
  'content': {
    template: '<pre :data-src="$basePath +\'static/demo-data/form/tree.json\'"></pre>',
    mounted: function mounted() {
      __WEBPACK_IMPORTED_MODULE_0_prismjs___default.a.highlightAll();
      __WEBPACK_IMPORTED_MODULE_0_prismjs___default.a.fileHighlight();
    }
  },
  'show': false
}, {
  'title': '联动规则',
  'url': 'static/demo-data/form/linkage-desc.html',
  'show': false
}]);

/***/ }),
/* 108 */
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_0_prismjs__ = __webpack_require__(5);
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_0_prismjs___default = __webpack_require__.n(__WEBPACK_IMPORTED_MODULE_0_prismjs__);
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_1__TestSample_vue__ = __webpack_require__(239);
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_1__TestSample_vue___default = __webpack_require__.n(__WEBPACK_IMPORTED_MODULE_1__TestSample_vue__);


/* harmony default export */ __webpack_exports__["a"] = ([{
  'title': 'tab1',
  'content': 'just string content',
  'show': true
}, {
  'title': 'tab2',
  'content': {
    template: '<h2>测试,inline component</h2>'
  },
  'show': false
}, {
  'title': 'tab3',
  'content': __WEBPACK_IMPORTED_MODULE_1__TestSample_vue___default.a,
  'show': false
}, {
  'title': 'tab4',
  'content': function content(resolve) {
    setTimeout(function () {
      resolve({
        template: '<h2>异步组件</h2>'
      });
    }, 3000);
  },
  'show': false
}, {
  'title': 'code',
  'content': {
    template: '<pre :data-src="$basePath +\'static/demo-data/tab.html\'"></pre>'
  },
  'show': false
}, {
  'title': 'data',
  'content': {
    template: '<pre :data-src="$basePath +\'static/demo-data/tab.js\'"></pre>',
    mounted: function mounted() {
      __WEBPACK_IMPORTED_MODULE_0_prismjs___default.a.highlightAll();
      __WEBPACK_IMPORTED_MODULE_0_prismjs___default.a.fileHighlight();
    }
  },
  'show': false
}]);

/***/ }),
/* 109 */
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_0_prismjs__ = __webpack_require__(5);
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_0_prismjs___default = __webpack_require__.n(__WEBPACK_IMPORTED_MODULE_0_prismjs__);

/* harmony default export */ __webpack_exports__["a"] = ([{
  'title': 'table代码',
  'content': {
    template: '<pre :data-src="$basePath +\'static/demo-data/table/Table.vue\'"></pre>'
  },
  'show': true
}, {
  'title': 'table数据格式',
  'content': {
    template: '<pre :data-src="$basePath +\'static/demo-data/table/table.json\'"></pre>',
    mounted: function mounted() {
      __WEBPACK_IMPORTED_MODULE_0_prismjs___default.a.highlightAll();
      __WEBPACK_IMPORTED_MODULE_0_prismjs___default.a.fileHighlight();
    }
  },
  'show': false
}]);

/***/ }),
/* 110 */
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_0_prismjs__ = __webpack_require__(5);
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_0_prismjs___default = __webpack_require__.n(__WEBPACK_IMPORTED_MODULE_0_prismjs__);

/* harmony default export */ __webpack_exports__["a"] = ([{
  'title': '代码',
  'content': {
    template: '<pre :data-src="$basePath +\'static/demo-data/treeWithTable/TreeWithTable.vue\'"></pre>'
  },
  'show': true
}, {
  'title': 'Tree数据格式',
  'content': {
    template: '<pre :data-src="$basePath +\'static/demo-data/treeWithTable/tree.json\'"></pre>',
    mounted: function mounted() {
      __WEBPACK_IMPORTED_MODULE_0_prismjs___default.a.highlightAll();
      __WEBPACK_IMPORTED_MODULE_0_prismjs___default.a.fileHighlight();
    }
  },
  'show': false
}]);

/***/ }),
/* 111 */
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_0_prismjs__ = __webpack_require__(5);
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_0_prismjs___default = __webpack_require__.n(__WEBPACK_IMPORTED_MODULE_0_prismjs__);

/* harmony default export */ __webpack_exports__["a"] = ([{
  'title': 'tree代码',
  'content': {
    template: '<pre :data-src="$basePath +\'static/demo-data/tree/Tree.vue\'"></pre>'
  },
  'show': true
}, {
  'title': 'tree数据格式',
  'content': {
    template: '<pre :data-src="$basePath +\'static/demo-data/tree/tree.json\'"></pre>',
    mounted: function mounted() {
      __WEBPACK_IMPORTED_MODULE_0_prismjs___default.a.highlightAll();
      __WEBPACK_IMPORTED_MODULE_0_prismjs___default.a.fileHighlight();
    }
  },
  'show': false
}]);

/***/ }),
/* 112 */
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
Object.defineProperty(__webpack_exports__, "__esModule", { value: true });
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_0_vue__ = __webpack_require__(7);
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_1__router__ = __webpack_require__(77);
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_2_vuex__ = __webpack_require__(15);
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_3__App__ = __webpack_require__(80);
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_3__App___default = __webpack_require__.n(__WEBPACK_IMPORTED_MODULE_3__App__);
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_4_huodh_vue_plugins__ = __webpack_require__(1);
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_4_huodh_vue_plugins___default = __webpack_require__.n(__WEBPACK_IMPORTED_MODULE_4_huodh_vue_plugins__);





__WEBPACK_IMPORTED_MODULE_0_vue__["default"].use(__WEBPACK_IMPORTED_MODULE_2_vuex__["default"]);
__WEBPACK_IMPORTED_MODULE_0_vue__["default"].use(__WEBPACK_IMPORTED_MODULE_4_huodh_vue_plugins___default.a.config);
var store = __WEBPACK_IMPORTED_MODULE_4_huodh_vue_plugins___default.a.generateStore();

new __WEBPACK_IMPORTED_MODULE_0_vue__["default"]({
  el: '#app',
  router: __WEBPACK_IMPORTED_MODULE_1__router__["a" /* default */],
  store: store,
  template: '<App/>',
  components: { App: __WEBPACK_IMPORTED_MODULE_3__App___default.a }
});

/***/ }),
/* 113 */,
/* 114 */,
/* 115 */
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
Object.defineProperty(__webpack_exports__, "__esModule", { value: true });
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_0_huodh_vue_plugins__ = __webpack_require__(1);
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_0_huodh_vue_plugins___default = __webpack_require__.n(__WEBPACK_IMPORTED_MODULE_0_huodh_vue_plugins__);
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_1__common__ = __webpack_require__(10);




var tree = __WEBPACK_IMPORTED_MODULE_0_huodh_vue_plugins___default.a.tree;

/* harmony default export */ __webpack_exports__["default"] = ({
  name: 'index',
  data: function data() {
    return {
      treeData: __WEBPACK_IMPORTED_MODULE_1__common__["a" /* commonUrls */].leftTree
    };
  },

  components: {
    tree: tree
  }
});

/***/ }),
/* 116 */
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
Object.defineProperty(__webpack_exports__, "__esModule", { value: true });
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_0_babel_runtime_core_js_json_stringify__ = __webpack_require__(23);
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_0_babel_runtime_core_js_json_stringify___default = __webpack_require__.n(__WEBPACK_IMPORTED_MODULE_0_babel_runtime_core_js_json_stringify__);
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_1_huodh_vue_plugins__ = __webpack_require__(1);
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_1_huodh_vue_plugins___default = __webpack_require__.n(__WEBPACK_IMPORTED_MODULE_1_huodh_vue_plugins__);
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_2__data__ = __webpack_require__(102);





var panel = __WEBPACK_IMPORTED_MODULE_1_huodh_vue_plugins___default.a.panel;
var tab = __WEBPACK_IMPORTED_MODULE_1_huodh_vue_plugins___default.a.tab;
var vform = __WEBPACK_IMPORTED_MODULE_1_huodh_vue_plugins___default.a.vform;
var formLineAdd = { add1: false, add2: false };
/* harmony default export */ __webpack_exports__["default"] = ({
  data: function data() {
    return {
      tabData: __WEBPACK_IMPORTED_MODULE_2__data__["a" /* default */],
      actions: {
        init: function init(params) {
          if (params.key) {
            console.log('get data by key:' + params.key);
          }
          console.log('deal with the init data by this function itself');
          return {
            'rules': {
              'items': [{
                'name': 'name',
                'label': '姓名',
                'type': 'text',
                'validate': [{
                  'errorMsg': '不能为空',
                  'regex': '^\\S+$'
                }],
                'defaultValue': '姓名',
                'placeholder': '请输入姓名',
                'locked': false,
                'error': true
              }, {
                'name': 'testPassword',
                'label': '测试Password',
                'type': 'password'
              }, {
                'name': 'testNumber',
                'label': '测试Number',
                'type': 'number',
                'validate': [{
                  'errorMsg': '不能为空',
                  'regex': '^\\S+$'
                }]
              }, {
                'name': 'testFile',
                'label': '测试File',
                'type': 'file',
                'quantity': 5,
                'validate': [{
                  'errorMsg': '只能为图片文件',
                  'regex': '\\.(png|jpe?g|gif|svg)(\\?.*)?$'
                }],
                'maxSize': 5000,
                'required': true,
                'path': ['http://www.hopever.cn/mogilefs/images/user/photo/14817789496788475104059462733375755.jpg']
              }, {
                'name': 'testDate',
                'label': '测试日期',
                'type': 'date',
                'validate': [{
                  'errorMsg': '不能为空',
                  'regex': '^\\S+$'
                }]
              }, {
                'name': 'testDateRange',
                'label': '测试范围日期',
                'type': 'daterange',
                'validate': [{
                  'errorMsg': '不能为空',
                  'regex': '^\\S+$'
                }]
              }, {
                'name': 'testSelect',
                'label': '测试select',
                'type': 'select',
                'validate': [{
                  'errorMsg': '不能为空',
                  'regex': '^\\S+$'
                }],
                'defaultValue': '2',
                'items': [{
                  'label': '测试1',
                  'value': '1'
                }, {
                  'label': '测试2',
                  'value': '2'
                }, {
                  'label': '测试3',
                  'value': '3'
                }]
              }, {
                'name': 'testRadio',
                'label': '测试Radio',
                'type': 'radio',
                'validate': [{
                  'errorMsg': '不能为空',
                  'regex': '^\\S+$'
                }],
                'defaultValue': '1',
                'items': [{
                  'label': '测试1',
                  'value': '1'
                }, {
                  'label': '测试2',
                  'value': '2'
                }, {
                  'label': '测试3',
                  'value': '3'
                }],
                'ruleChange': true
              }, {
                'name': 'ruleChange1',
                'label': '联动1',
                'type': 'text',
                'validate': [{
                  'errorMsg': '不能为空',
                  'regex': '^\\S+$'
                }],
                'defaultValue': '联动测试1',
                'placeholder': '联动测试1',
                'locked': false,
                'hidden': false,
                'error': true
              }, {
                'name': 'ruleChange2',
                'label': '联动2',
                'type': 'text',
                'validate': [{
                  'errorMsg': '不能为空',
                  'regex': '^\\S+$'
                }],
                'defaultValue': '联动测试2',
                'placeholder': '联动测试2',
                'locked': false,
                'hidden': true,
                'error': true
              }, {
                'name': 'ruleChange3',
                'label': '联动3',
                'type': 'text',
                'validate': [{
                  'errorMsg': '不能为空',
                  'regex': '^\\S+$'
                }],
                'defaultValue': '联动测试3',
                'placeholder': '联动测试3',
                'locked': false,
                'hidden': true,
                'error': true
              }, {
                'name': 'testCheckbox',
                'label': '测试Checkbox',
                'type': 'checkbox',
                'validate': [{
                  'errorMsg': '不能为空',
                  'regex': '^\\S+$'
                }],
                'defaultValue': ['1', '2'],
                'items': [{
                  'label': '测试1',
                  'value': '1'
                }, {
                  'label': '测试2',
                  'value': '2'
                }, {
                  'label': '测试3',
                  'value': '3'
                }]
              }, {
                'name': 'testTree',
                'label': '下拉树控件',
                'type': 'tree',
                'treeData': '/static/demo-data/form/tree.json',
                'defaultLabel': '测试3',
                'defaultValue': '3',
                'validate': [{
                  'errorMsg': '不能为空',
                  'regex': '^\\S+$'
                }],
                'placeholder': '点击选择树'
              }, {
                'name': 'testTextArea',
                'label': '测试TextArea',
                'type': 'textarea',
                'validate': [{
                  'errorMsg': '不能为空',
                  'regex': '^\\S+$'
                }],
                'defaultValue': '看一看，瞧一瞧',
                'rows': 10,
                'placeholder': 'this is textarea'
              }, [{
                'name': 'testInline1',
                'label': '测试Inline1',
                'type': 'text',
                'validate': [{
                  'errorMsg': '不能为空',
                  'regex': '^\\S+$'
                }]
              }, {
                'name': 'testInline2',
                'label': '测试Inline2',
                'type': 'text'
              }, {
                'name': 'addLine',
                'label': '添加新行',
                'defaultValue': '添加新行',
                'type': 'button',
                'ruleChange': true
              }], [{
                'name': 'testInline4',
                'label': '测试Inline4',
                'type': 'text',
                'validate': [{
                  'errorMsg': '不能为空',
                  'regex': '^\\S+$'
                }],
                'hidden': true
              }, {
                'name': 'testInline5',
                'label': '测试Inline5',
                'type': 'text',
                'hidden': true
              }, {
                'name': 'delLine1',
                'label': '删除本行',
                'defaultValue': '删除本行',
                'type': 'button',
                'ruleChange': true,
                'hidden': true
              }], [{
                'name': 'testInline6',
                'label': '测试Inline6',
                'type': 'text',
                'validate': [{
                  'errorMsg': '不能为空',
                  'regex': '^\\S+$'
                }],
                'hidden': true
              }, {
                'name': 'testInline7',
                'label': '测试Inline7',
                'type': 'text',
                'hidden': true
              }, {
                'name': 'delLine2',
                'label': '删除本行',
                'defaultValue': '删除本行',
                'type': 'button',
                'ruleChange': true,
                'hidden': true
              }]],
              'action': {
                'save': {
                  'label': '保存'
                },
                'reset': {
                  'label': '重置'
                },
                'others': [{
                  'key': 'sampleTest',
                  'label': 'SampleTest'
                }]
              }
            }
          };
        },
        save: function save(params) {
          if (params.key) {
            console.log('save data by key:' + params.key);
          }
          if (params.data) {
            console.log('these data will be saved:' + __WEBPACK_IMPORTED_MODULE_0_babel_runtime_core_js_json_stringify___default()(params.data));
          }
          if (params.multipart) {
            console.log('we shall transfer these data to server by multipart type');
          }
          console.log('deal with the data by this save function itself');
          return {
            success: {
              title: '保存数据',
              message: '保存成功'
            }
          };
        },
        ruleChange: function ruleChange(params) {
          if (params.parameters) {
            console.log('change rule data by key:' + __WEBPACK_IMPORTED_MODULE_0_babel_runtime_core_js_json_stringify___default()(params.data));
          }
          var data = params.parameters;
          if (data && data.testRadio) {
            if (data.testRadio === '1') {
              return [{
                'name': 'ruleChange1',
                'hidden': false
              }, {
                'name': 'ruleChange2',
                'hidden': true
              }, {
                'name': 'ruleChange3',
                'hidden': true
              }];
            } else if (data.testRadio === '2') {
              return [{
                'name': 'ruleChange1',
                'hidden': true
              }, {
                'name': 'ruleChange2',
                'hidden': false
              }, {
                'name': 'ruleChange3',
                'hidden': true
              }];
            } else if (data.testRadio === '3') {
              return [{
                'name': 'ruleChange1',
                'hidden': true
              }, {
                'name': 'ruleChange2',
                'hidden': true
              }, {
                'name': 'ruleChange3',
                'defaultValue': '联动测试3' + new Date().getMilliseconds(),
                'hidden': false
              }];
            }
          }
          if (data && data.addLine) {
            if (!formLineAdd.add1) {
              formLineAdd.add1 = true;
              return [{
                'name': 'testInline4',
                'hidden': false
              }, {
                'name': 'testInline5',
                'hidden': false
              }, {
                'name': 'delLine1',
                'hidden': false
              }];
            } else if (!formLineAdd.add2) {
              formLineAdd.add2 = true;
              return [{
                'name': 'testInline6',
                'hidden': false
              }, {
                'name': 'testInline7',
                'hidden': false
              }, {
                'name': 'delLine2',
                'hidden': false
              }];
            }
          }
          if (data && data.delLine1) {
            formLineAdd.add1 = false;
            return [{
              'name': 'testInline4',
              'hidden': true
            }, {
              'name': 'testInline5',
              'hidden': true
            }, {
              'name': 'delLine1',
              'hidden': true
            }];
          }
          if (data && data.delLine2) {
            formLineAdd.add2 = false;
            return [{
              'name': 'testInline6',
              'hidden': true
            }, {
              'name': 'testInline7',
              'hidden': true
            }, {
              'name': 'delLine2',
              'hidden': true
            }];
          }
        },
        reset: function reset(params) {
          if (params.key) {
            console.log('reset form by key:' + params.key);
          }
          formLineAdd = { add1: false, add2: false };
        },
        sampleTest: function sampleTest(params) {
          if (params.key) {
            console.log('reset form by key:' + params.key);
          }
          console.log('right be here');
        }
      }
    };
  },

  components: {
    panel: panel, tab: tab, vform: vform
  }
});

/***/ }),
/* 117 */
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
Object.defineProperty(__webpack_exports__, "__esModule", { value: true });
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_0_lodash__ = __webpack_require__(47);
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_0_lodash___default = __webpack_require__.n(__WEBPACK_IMPORTED_MODULE_0_lodash__);
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_1_huodh_vue_plugins__ = __webpack_require__(1);
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_1_huodh_vue_plugins___default = __webpack_require__.n(__WEBPACK_IMPORTED_MODULE_1_huodh_vue_plugins__);
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_2__common__ = __webpack_require__(10);
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_3__data__ = __webpack_require__(103);







var panel = __WEBPACK_IMPORTED_MODULE_1_huodh_vue_plugins___default.a.panel;
var tab = __WEBPACK_IMPORTED_MODULE_1_huodh_vue_plugins___default.a.tab;
var vtable = __WEBPACK_IMPORTED_MODULE_1_huodh_vue_plugins___default.a.vtable;
var tableData = [{
  'key': 1,
  'value': ['小一', [1, 2], 1, '', 1493568000000]
}, {
  'key': 2,
  'value': ['小二', '', '', '', 1493568000000]
}, {
  'key': 3,
  'value': ['小3', '', '', '', 1493568000000]
}, {
  'key': 4,
  'value': ['小4', '', '', '', 1493568000000]
}, {
  'key': 5,
  'value': ['小5', '', '', '', 1493568000000]
}, {
  'key': 6,
  'value': ['小6', '', '', '', 1493568000000]
}, {
  'key': 7,
  'value': ['小7', '', '', '', 1493568000000]
}, {
  'key': 8,
  'value': ['小8', '', '', '', 1493568000000]
}, {
  'key': 9,
  'value': ['小9', '', '', '', 1493568000000]
}, {
  'key': 10,
  'value': ['小10', '', '', '', 1493568000000]
}, {
  'key': 11,
  'value': ['小1一', '', '', '', 1493568000000]
}, {
  'key': 12,
  'value': ['小1二', '', '', '', 1493568000000]
}, {
  'key': 13,
  'value': ['小13', '', '', '', 1493568000000]
}, {
  'key': 14,
  'value': ['小14', '', '', '', 1493568000000]
}, {
  'key': 15,
  'value': ['小15', '', '', '', 1493568000000]
}, {
  'key': 16,
  'value': ['小16', '', '', '', 1493568000000]
}, {
  'key': 17,
  'value': ['小17', '', '', '', 1493568000000]
}, {
  'key': 18,
  'value': ['小18', '', '', '', 1493568000000]
}, {
  'key': 19,
  'value': ['小19', '', '', '', 1493568000000]
}, {
  'key': 20,
  'value': ['小20', '', '', '', 1493568000000]
}, {
  'key': 21,
  'value': ['小2一', '', '', '', 1493568000000]
}, {
  'key': 22,
  'value': ['小2二', '', '', '', 1493568000000]
}, {
  'key': 23,
  'value': ['小23', '', '', '', 1493568000000]
}];
/* harmony default export */ __webpack_exports__["default"] = ({
  data: function data() {
    return {
      tabData: __WEBPACK_IMPORTED_MODULE_3__data__["a" /* default */],
      actionUrls: {
        addUrl: __WEBPACK_IMPORTED_MODULE_2__common__["a" /* commonUrls */].vuerouter.testForm,
        detailUrl: __WEBPACK_IMPORTED_MODULE_2__common__["a" /* commonUrls */].testTableInit,
        infoUrl: __WEBPACK_IMPORTED_MODULE_2__common__["a" /* commonUrls */].testTableInit
      },
      actions: {
        list: function list(args) {
          console.log('do get list by actions list function');
          var pager = args.pager;
          var init = args.init;
          var filters = args.filters;
          var sorts = args.sorts;
          var subData = tableData;
          if (filters && filters.name) {
            subData = __WEBPACK_IMPORTED_MODULE_0_lodash___default.a.filter(tableData, function (o) {
              return o.value[0].indexOf(filters.name) > -1;
            });
          }
          if (filters && filters.testDate) {
            subData = __WEBPACK_IMPORTED_MODULE_0_lodash___default.a.filter(subData, function (o) {
              return +o.value[4] < filters.testDate;
            });
          }
          if (filters && filters.testSelect) {
            subData = __WEBPACK_IMPORTED_MODULE_0_lodash___default.a.filter(subData, function (o) {
              return +o.value[2] === filters.testSelect;
            });
          }
          if (sorts && sorts.name) {
            __WEBPACK_IMPORTED_MODULE_0_lodash___default.a.reverse(subData);
          }
          if ((pager.currentPage - 1) * pager.pageSize >= subData.length) {
            pager.currentPage = Math.ceil(subData.length / pager.pageSize);
            if (pager.currentPage === 0) {
              pager.currentPage = 1;
            }
          }
          var returnData = subData.slice((pager.currentPage - 1) * pager.pageSize, pager.currentPage * pager.pageSize);
          if (init) {
            return {
              'rules': {
                'header': [{
                  'name': '#sn',
                  'title': '#sn'
                }, {
                  'name': 'name',
                  'title': '名称',
                  'type': 'text',
                  'editable': true,
                  'filter': true,
                  'sortable': true
                }, {
                  'name': 'testCheckbox',
                  'title': '测试多选',
                  'type': 'checkbox',
                  'editable': true,
                  'items': [{
                    'label': '测试1',
                    'value': 1
                  }, {
                    'label': '测试2',
                    'value': 2
                  }, {
                    'label': '测试3',
                    'value': 3
                  }]
                }, {
                  'name': 'testSelect',
                  'title': '测试select',
                  'type': 'select',
                  'editable': true,
                  'filter': true,
                  'items': [{
                    'label': '测试1',
                    'value': 1
                  }, {
                    'label': '测试2',
                    'value': 2
                  }, {
                    'label': '测试3',
                    'value': 3
                  }]
                }, {
                  'name': 'testImg',
                  'title': '测试图片',
                  'type': 'image',
                  'editable': true
                }, {
                  'name': 'testDate',
                  'title': '在此之前',
                  'editable': true,
                  'type': 'date',
                  'filter': true
                }],
                'action': {
                  'add': true,
                  'detail': true,
                  'update': true,
                  'delete': true
                },
                'feature': {
                  'filter': true,
                  'pager': true
                }
              },
              'data': {
                'rows': subData.slice(0, pager.pageSize),
                'totalCount': subData.length,
                pager: pager
              }
            };
          } else {
            return {
              'data': {
                'rows': returnData,
                'totalCount': subData.length,
                pager: pager,
                filters: filters,
                sorts: sorts
              }
            };
          }
        },
        delete: function _delete(args) {
          console.log('do delete by actions delete function');
          var id = parseInt(args.key);
          __WEBPACK_IMPORTED_MODULE_0_lodash___default.a.remove(tableData, function (o) {
            return o.key === id;
          });
        },
        edit: function edit(args) {
          console.log('this is for editing');
          if (args && args.headerItem && (args.headerItem.type === 'file' || args.headerItem.type === 'image')) {
            return 'http://www.hopever.cn/mogilefs/images/user/photo/14817789496788475104059462733375755.jpg';
          }
        }
      }
    };
  },

  components: {
    panel: panel, tab: tab, vtable: vtable
  }
});

/***/ }),
/* 118 */
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
Object.defineProperty(__webpack_exports__, "__esModule", { value: true });
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_0_babel_runtime_core_js_json_stringify__ = __webpack_require__(23);
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_0_babel_runtime_core_js_json_stringify___default = __webpack_require__.n(__WEBPACK_IMPORTED_MODULE_0_babel_runtime_core_js_json_stringify__);
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_1_huodh_vue_plugins__ = __webpack_require__(1);
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_1_huodh_vue_plugins___default = __webpack_require__.n(__WEBPACK_IMPORTED_MODULE_1_huodh_vue_plugins__);
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_2__data__ = __webpack_require__(104);





var panel = __WEBPACK_IMPORTED_MODULE_1_huodh_vue_plugins___default.a.panel;
var tab = __WEBPACK_IMPORTED_MODULE_1_huodh_vue_plugins___default.a.tab;
var tree = __WEBPACK_IMPORTED_MODULE_1_huodh_vue_plugins___default.a.tree;
/* harmony default export */ __webpack_exports__["default"] = ({
  name: 'tree-example',
  data: function data() {
    return {
      tabData: __WEBPACK_IMPORTED_MODULE_2__data__["a" /* default */],
      actions: {
        tree: function tree() {
          return [{
            'id': 1,
            'title': 'Sample1',
            'iconClass': 'glyphicon glyphicon-th-large',
            'deletable': false,
            'children': [{
              'id': 2,
              'title': 'Panel'
            }, {
              'id': 3,
              'title': 'Tab'
            }, {
              'id': 4,
              'title': 'Modal'
            }]
          }, {
            'id': 5,
            'title': 'Sample2',
            'iconClass': 'glyphicon glyphicon-th-large',
            'children': [{
              'id': 6,
              'title': 'Form',
              'iconClass': 'glyphicon glyphicon-th-large',
              'children': [{
                'id': 7,
                'title': 'Tree'
              }, {
                'id': 8,
                'title': 'Date'
              }, {
                'id': 9,
                'title': 'TestClick'
              }, {
                'id': 10,
                'title': 'OnlyText'
              }]
            }, {
              'id': 11,
              'title': 'Tree'
            }, {
              'id': 12,
              'title': 'Date'
            }, {
              'id': 13,
              'title': 'TestClick'
            }, {
              'id': 14,
              'title': 'OnlyText'
            }]
          }];
        },
        add: function add(params) {
          if (params.key) {
            console.log('save data by key:' + params.key);
          }
          if (params.data) {
            console.log('these data will be saved:' + __WEBPACK_IMPORTED_MODULE_0_babel_runtime_core_js_json_stringify___default()(params.data));
          }
          if (params.multipart) {
            console.log('we shall transfer these data to server by multipart type');
          }
          console.log('save the tree data by this save function itself');
        },
        edit: function edit(params) {
          if (params.key) {
            console.log('save data by key:' + params.key);
          }
          if (params.data) {
            console.log('these data will be saved:' + __WEBPACK_IMPORTED_MODULE_0_babel_runtime_core_js_json_stringify___default()(params.data));
          }
          if (params.multipart) {
            console.log('we shall transfer these data to server by multipart type');
          }
          console.log('update the tree data by this save function itself');
        },
        delete: function _delete(params) {
          console.log('delete tree item with id:' + params.id);
        }
      }
    };
  },

  components: {
    panel: panel, tab: tab, tree: tree
  },
  methods: {}
});

/***/ }),
/* 119 */
/***/ (function(module, exports) {



/***/ }),
/* 120 */
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
Object.defineProperty(__webpack_exports__, "__esModule", { value: true });
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_0_huodh_vue_plugins__ = __webpack_require__(1);
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_0_huodh_vue_plugins___default = __webpack_require__.n(__WEBPACK_IMPORTED_MODULE_0_huodh_vue_plugins__);
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_1__data__ = __webpack_require__(105);




var panel = __WEBPACK_IMPORTED_MODULE_0_huodh_vue_plugins___default.a.panel;
var tab = __WEBPACK_IMPORTED_MODULE_0_huodh_vue_plugins___default.a.tab;
var vform = __WEBPACK_IMPORTED_MODULE_0_huodh_vue_plugins___default.a.vform;
var datePicker = __WEBPACK_IMPORTED_MODULE_0_huodh_vue_plugins___default.a.datePicker;
/* harmony default export */ __webpack_exports__["default"] = ({
  data: function data() {
    console.log(new Date('2017-03-11').getTime());
    console.log(new Date('2017-04-11').getTime());
    return {
      dateData: __WEBPACK_IMPORTED_MODULE_1__data__["a" /* default */],
      dateInit: 1489190400000,
      dateRangeInit: [1489190400000, 1491868800000]
    };
  },

  components: {
    panel: panel, tab: tab, vform: vform, datePicker: datePicker
  }
});

/***/ }),
/* 121 */
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
Object.defineProperty(__webpack_exports__, "__esModule", { value: true });
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_0_huodh_vue_plugins__ = __webpack_require__(1);
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_0_huodh_vue_plugins___default = __webpack_require__.n(__WEBPACK_IMPORTED_MODULE_0_huodh_vue_plugins__);
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_1__common__ = __webpack_require__(10);
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_2__data__ = __webpack_require__(106);





var panel = __WEBPACK_IMPORTED_MODULE_0_huodh_vue_plugins___default.a.panel;
var tab = __WEBPACK_IMPORTED_MODULE_0_huodh_vue_plugins___default.a.tab;
var tree = __WEBPACK_IMPORTED_MODULE_0_huodh_vue_plugins___default.a.tree;
/* harmony default export */ __webpack_exports__["default"] = ({
  name: 'tree-example',
  data: function data() {
    return {
      tabData: __WEBPACK_IMPORTED_MODULE_2__data__["a" /* default */],
      treeData: __WEBPACK_IMPORTED_MODULE_1__common__["a" /* commonUrls */].testEditTreeInit,
      actionUrls: {
        addUrl: __WEBPACK_IMPORTED_MODULE_1__common__["a" /* commonUrls */].testEditTreeSave,
        editUrl: __WEBPACK_IMPORTED_MODULE_1__common__["a" /* commonUrls */].testEditTreeUpdate,
        deleteUrl: __WEBPACK_IMPORTED_MODULE_1__common__["a" /* commonUrls */].testEditTreeDelete
      }
    };
  },

  components: {
    panel: panel, tab: tab, tree: tree
  },
  methods: {}
});

/***/ }),
/* 122 */
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
Object.defineProperty(__webpack_exports__, "__esModule", { value: true });
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_0_huodh_vue_plugins__ = __webpack_require__(1);
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_0_huodh_vue_plugins___default = __webpack_require__.n(__WEBPACK_IMPORTED_MODULE_0_huodh_vue_plugins__);
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_1__common__ = __webpack_require__(10);
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_2__data__ = __webpack_require__(107);





var panel = __WEBPACK_IMPORTED_MODULE_0_huodh_vue_plugins___default.a.panel;
var tab = __WEBPACK_IMPORTED_MODULE_0_huodh_vue_plugins___default.a.tab;
var vform = __WEBPACK_IMPORTED_MODULE_0_huodh_vue_plugins___default.a.vform;
/* harmony default export */ __webpack_exports__["default"] = ({
  data: function data() {
    return {
      tabData: __WEBPACK_IMPORTED_MODULE_2__data__["a" /* default */],
      actionUrls: {
        initUrl: __WEBPACK_IMPORTED_MODULE_1__common__["a" /* commonUrls */].testFormInit,
        ruleChangeUrl: __WEBPACK_IMPORTED_MODULE_1__common__["a" /* commonUrls */].testFormRuleChange,
        saveUrl: __WEBPACK_IMPORTED_MODULE_1__common__["a" /* commonUrls */].testFormSave,
        resetUrl: __WEBPACK_IMPORTED_MODULE_1__common__["a" /* commonUrls */].testFormReset,
        backupUrl: __WEBPACK_IMPORTED_MODULE_1__common__["a" /* commonUrls */].vuerouter.testTable
      }
    };
  },

  components: {
    panel: panel, tab: tab, vform: vform
  }
});

/***/ }),
/* 123 */
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
Object.defineProperty(__webpack_exports__, "__esModule", { value: true });


/* harmony default export */ __webpack_exports__["default"] = ({
  data: function data() {
    return {
      dataDefault: {
        header: 'default Modal',
        showFooter: true,
        content: 'just test a single line',
        isConfirmModal: true
      }
    };
  },

  methods: {
    createDefaultModal: function createDefaultModal() {
      this.$Vue.createModal({ modalData: this.dataDefault, confirmCallback: this.triggerConfirm.bind(this) });
    },
    triggerConfirm: function triggerConfirm() {
      console.log('you have clicked the confirm button');
    }
  }
});

/***/ }),
/* 124 */
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
Object.defineProperty(__webpack_exports__, "__esModule", { value: true });


/* harmony default export */ __webpack_exports__["default"] = ({
  data: function data() {
    return {
      dataDefault: {
        type: 'danger',
        header: 'Danger Modal',
        showFooter: true,
        content: 'just test a single line'
      }
    };
  },

  methods: {
    createDefaultModal: function createDefaultModal() {
      this.$Vue.createModal({ modalData: this.dataDefault });
    }
  }
});

/***/ }),
/* 125 */
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
Object.defineProperty(__webpack_exports__, "__esModule", { value: true });


/* harmony default export */ __webpack_exports__["default"] = ({
  data: function data() {
    return {
      dataDefault: {
        header: 'default Modal',
        showFooter: true,
        content: 'just test a single line'
      }
    };
  },

  methods: {
    createDefaultModal: function createDefaultModal() {
      this.$Vue.createModal({ modalData: this.dataDefault });
    }
  }
});

/***/ }),
/* 126 */
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
Object.defineProperty(__webpack_exports__, "__esModule", { value: true });


/* harmony default export */ __webpack_exports__["default"] = ({
  data: function data() {
    return {
      dataDefault: {
        type: 'info',
        header: 'Info Modal',
        showFooter: true,
        content: 'just test a single line'
      }
    };
  },

  methods: {
    createDefaultModal: function createDefaultModal() {
      this.$Vue.createModal({ modalData: this.dataDefault });
    }
  }
});

/***/ }),
/* 127 */
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
Object.defineProperty(__webpack_exports__, "__esModule", { value: true });
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_0_prismjs__ = __webpack_require__(5);
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_0_prismjs___default = __webpack_require__.n(__WEBPACK_IMPORTED_MODULE_0_prismjs__);
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_1_huodh_vue_plugins__ = __webpack_require__(1);
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_1_huodh_vue_plugins___default = __webpack_require__.n(__WEBPACK_IMPORTED_MODULE_1_huodh_vue_plugins__);
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_2__DefaultModal_vue__ = __webpack_require__(231);
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_2__DefaultModal_vue___default = __webpack_require__.n(__WEBPACK_IMPORTED_MODULE_2__DefaultModal_vue__);
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_3__ModalNoFooter_vue__ = __webpack_require__(234);
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_3__ModalNoFooter_vue___default = __webpack_require__.n(__WEBPACK_IMPORTED_MODULE_3__ModalNoFooter_vue__);
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_4__ConfirmModal_vue__ = __webpack_require__(229);
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_4__ConfirmModal_vue___default = __webpack_require__.n(__WEBPACK_IMPORTED_MODULE_4__ConfirmModal_vue__);
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_5__PrimaryModal_vue__ = __webpack_require__(235);
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_5__PrimaryModal_vue___default = __webpack_require__.n(__WEBPACK_IMPORTED_MODULE_5__PrimaryModal_vue__);
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_6__SuccessModal_vue__ = __webpack_require__(236);
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_6__SuccessModal_vue___default = __webpack_require__.n(__WEBPACK_IMPORTED_MODULE_6__SuccessModal_vue__);
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_7__InfoModal_vue__ = __webpack_require__(232);
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_7__InfoModal_vue___default = __webpack_require__.n(__WEBPACK_IMPORTED_MODULE_7__InfoModal_vue__);
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_8__DangerModal_vue__ = __webpack_require__(230);
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_8__DangerModal_vue___default = __webpack_require__.n(__WEBPACK_IMPORTED_MODULE_8__DangerModal_vue__);











var panel = __WEBPACK_IMPORTED_MODULE_1_huodh_vue_plugins___default.a.panel;
var tab = __WEBPACK_IMPORTED_MODULE_1_huodh_vue_plugins___default.a.tab;
/* harmony default export */ __webpack_exports__["default"] = ({
  data: function data() {
    return {
      defaultModalData: [{
        title: 'default Modal',
        content: __WEBPACK_IMPORTED_MODULE_2__DefaultModal_vue___default.a,
        'show': true
      }, {
        title: 'code&data',
        content: {
          template: '<pre class=" language-javascript" :data-src="$basePath +\'static/demo-data/modal/DefaultModal.vue\'"></pre>'
        },
        'show': false
      }],
      modalNoFooterData: [{
        title: 'No footer Modal',
        content: __WEBPACK_IMPORTED_MODULE_3__ModalNoFooter_vue___default.a,
        'show': true
      }, {
        title: 'code&data',
        content: {
          template: '<pre class=" language-javascript" :data-src="$basePath +\'static/demo-data/modal/ModalNoFooter.vue\'"></pre>'
        },
        'show': false
      }],
      confirmModalData: [{
        title: 'Confirm Modal',
        content: __WEBPACK_IMPORTED_MODULE_4__ConfirmModal_vue___default.a,
        'show': true
      }, {
        title: 'code&data',
        content: {
          template: '<pre class=" language-javascript" :data-src="$basePath +\'static/demo-data/modal/ConfirmModal.vue\'"></pre>'
        },
        'show': false
      }],
      primaryModalData: [{
        title: 'Primary Modal',
        content: __WEBPACK_IMPORTED_MODULE_5__PrimaryModal_vue___default.a,
        'show': true
      }, {
        title: 'code&data',
        content: {
          template: '<pre class=" language-javascript" :data-src="$basePath +\'static/demo-data/modal/PrimaryModal.vue\'"></pre>'
        },
        'show': false
      }],
      successModalData: [{
        title: 'Success Modal',
        content: __WEBPACK_IMPORTED_MODULE_6__SuccessModal_vue___default.a,
        'show': true
      }, {
        title: 'code&data',
        content: {
          template: '<pre class=" language-javascript" :data-src="$basePath +\'static/demo-data/modal/SuccessModal.vue\'"></pre>'
        },
        'show': false
      }],
      infoModalData: [{
        title: 'Info Modal',
        content: __WEBPACK_IMPORTED_MODULE_7__InfoModal_vue___default.a,
        'show': true
      }, {
        title: 'code&data',
        content: {
          template: '<pre class=" language-javascript" :data-src="$basePath +\'static/demo-data/modal/InfoModal.vue\'"></pre>'
        },
        'show': false
      }],
      dangerModalData: [{
        title: 'Danger Modal',
        content: __WEBPACK_IMPORTED_MODULE_8__DangerModal_vue___default.a,
        'show': true
      }, {
        title: 'code&data',
        content: {
          template: '<pre class=" language-javascript" :data-src="$basePath +\'static/demo-data/modal/DangerModal.vue\'"></pre>',
          mounted: function mounted() {
            __WEBPACK_IMPORTED_MODULE_0_prismjs___default.a.highlightAll();
            __WEBPACK_IMPORTED_MODULE_0_prismjs___default.a.fileHighlight();
          }
        },
        'show': false
      }]
    };
  },

  components: { panel: panel, tab: tab }
});

/***/ }),
/* 128 */
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
Object.defineProperty(__webpack_exports__, "__esModule", { value: true });


/* harmony default export */ __webpack_exports__["default"] = ({
  data: function data() {
    return {
      dataDefault: {
        header: 'default Modal',
        showFooter: false,
        content: '没有底部的modal，用于嵌入其他不需要footer的module'
      }
    };
  },

  methods: {
    createDefaultModal: function createDefaultModal() {
      this.$Vue.createModal({ modalData: this.dataDefault });
    }
  }
});

/***/ }),
/* 129 */
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
Object.defineProperty(__webpack_exports__, "__esModule", { value: true });


/* harmony default export */ __webpack_exports__["default"] = ({
  data: function data() {
    return {
      dataDefault: {
        type: 'primary',
        header: 'Primary Modal',
        showFooter: true,
        content: 'just test a single line'
      }
    };
  },

  methods: {
    createDefaultModal: function createDefaultModal() {
      this.$Vue.createModal({ modalData: this.dataDefault });
    }
  }
});

/***/ }),
/* 130 */
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
Object.defineProperty(__webpack_exports__, "__esModule", { value: true });


/* harmony default export */ __webpack_exports__["default"] = ({
  data: function data() {
    return {
      dataDefault: {
        type: 'success',
        header: 'Success Modal',
        showFooter: true,
        content: 'just test a single line'
      }
    };
  },

  methods: {
    createDefaultModal: function createDefaultModal() {
      this.$Vue.createModal({ modalData: this.dataDefault });
    }
  }
});

/***/ }),
/* 131 */
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
Object.defineProperty(__webpack_exports__, "__esModule", { value: true });
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_0_prismjs__ = __webpack_require__(5);
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_0_prismjs___default = __webpack_require__.n(__WEBPACK_IMPORTED_MODULE_0_prismjs__);
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_1_huodh_vue_plugins__ = __webpack_require__(1);
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_1_huodh_vue_plugins___default = __webpack_require__.n(__WEBPACK_IMPORTED_MODULE_1_huodh_vue_plugins__);




var panel = __WEBPACK_IMPORTED_MODULE_1_huodh_vue_plugins___default.a.panel;
/* harmony default export */ __webpack_exports__["default"] = ({
  components: {
    panel: panel
  },
  mounted: function mounted() {
    __WEBPACK_IMPORTED_MODULE_0_prismjs___default.a.fileHighlight();
  }
});

/***/ }),
/* 132 */
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
Object.defineProperty(__webpack_exports__, "__esModule", { value: true });
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_0_huodh_vue_plugins__ = __webpack_require__(1);
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_0_huodh_vue_plugins___default = __webpack_require__.n(__WEBPACK_IMPORTED_MODULE_0_huodh_vue_plugins__);
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_1__data__ = __webpack_require__(108);




var panel = __WEBPACK_IMPORTED_MODULE_0_huodh_vue_plugins___default.a.panel;
var tab = __WEBPACK_IMPORTED_MODULE_0_huodh_vue_plugins___default.a.tab;
/* harmony default export */ __webpack_exports__["default"] = ({
  data: function data() {
    return {
      tabData: __WEBPACK_IMPORTED_MODULE_1__data__["a" /* default */]
    };
  },

  components: {
    panel: panel, tab: tab
  }
});

/***/ }),
/* 133 */
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
Object.defineProperty(__webpack_exports__, "__esModule", { value: true });
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_0_huodh_vue_plugins__ = __webpack_require__(1);
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_0_huodh_vue_plugins___default = __webpack_require__.n(__WEBPACK_IMPORTED_MODULE_0_huodh_vue_plugins__);
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_1__common__ = __webpack_require__(10);
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_2__data__ = __webpack_require__(109);





var panel = __WEBPACK_IMPORTED_MODULE_0_huodh_vue_plugins___default.a.panel;
var tab = __WEBPACK_IMPORTED_MODULE_0_huodh_vue_plugins___default.a.tab;
var vtable = __WEBPACK_IMPORTED_MODULE_0_huodh_vue_plugins___default.a.vtable;
/* harmony default export */ __webpack_exports__["default"] = ({
  data: function data() {
    return {
      tabData: __WEBPACK_IMPORTED_MODULE_2__data__["a" /* default */],
      actionUrls: {
        listUrl: __WEBPACK_IMPORTED_MODULE_1__common__["a" /* commonUrls */].testTableInit,
        addUrl: __WEBPACK_IMPORTED_MODULE_1__common__["a" /* commonUrls */].vuerouter.testForm,
        detailUrl: __WEBPACK_IMPORTED_MODULE_1__common__["a" /* commonUrls */].testTableInit,
        deleteUrl: __WEBPACK_IMPORTED_MODULE_1__common__["a" /* commonUrls */].testTableRowDel,
        infoUrl: __WEBPACK_IMPORTED_MODULE_1__common__["a" /* commonUrls */].testTableInit
      },
      actions: {
        edit: function edit(args) {
          console.log('this is for editing');
          if (args && args.headerItem && (args.headerItem.type === 'file' || args.headerItem.type === 'image')) {
            return 'http://www.hopever.cn/mogilefs/images/user/photo/14817789496788475104059462733375755.jpg';
          }
        }
      }
    };
  },

  components: {
    panel: panel, tab: tab, vtable: vtable
  }
});

/***/ }),
/* 134 */
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
Object.defineProperty(__webpack_exports__, "__esModule", { value: true });
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_0_lodash__ = __webpack_require__(47);
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_0_lodash___default = __webpack_require__.n(__WEBPACK_IMPORTED_MODULE_0_lodash__);
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_1_huodh_vue_plugins__ = __webpack_require__(1);
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_1_huodh_vue_plugins___default = __webpack_require__.n(__WEBPACK_IMPORTED_MODULE_1_huodh_vue_plugins__);
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_2__common__ = __webpack_require__(10);
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_3__data__ = __webpack_require__(110);






var panel = __WEBPACK_IMPORTED_MODULE_1_huodh_vue_plugins___default.a.panel;
var tab = __WEBPACK_IMPORTED_MODULE_1_huodh_vue_plugins___default.a.tab;
var vtable = __WEBPACK_IMPORTED_MODULE_1_huodh_vue_plugins___default.a.vtable;
var tree = __WEBPACK_IMPORTED_MODULE_1_huodh_vue_plugins___default.a.tree;
/* harmony default export */ __webpack_exports__["default"] = ({
  data: function data() {
    return {
      tabData: __WEBPACK_IMPORTED_MODULE_3__data__["a" /* default */],
      treeData: __WEBPACK_IMPORTED_MODULE_2__common__["a" /* commonUrls */].testTreeWithTableInit,
      actionUrls: {
        listUrl: __WEBPACK_IMPORTED_MODULE_2__common__["a" /* commonUrls */].testTableForTreeInit
      }
    };
  },

  components: {
    panel: panel, tab: tab, vtable: vtable, tree: tree
  },
  methods: {
    changeTableData: function changeTableData(limit) {
      var _ref = limit || {},
          _ref$from = _ref.from,
          from = _ref$from === undefined ? 0 : _ref$from,
          _ref$to = _ref.to,
          to = _ref$to === undefined ? 0 : _ref$to;

      if (!from) {
        this.actionUrls = __WEBPACK_IMPORTED_MODULE_0_lodash___default.a.assign({}, this.actionUrls, {
          listUrl: __WEBPACK_IMPORTED_MODULE_2__common__["a" /* commonUrls */].testTableForTreeInit
        });
      } else if (from && !to) {
        this.actionUrls = __WEBPACK_IMPORTED_MODULE_0_lodash___default.a.assign({}, this.actionUrls, {
          listUrl: __WEBPACK_IMPORTED_MODULE_2__common__["a" /* commonUrls */].testTableForTreeInit + '?from=' + from
        });
      } else {
        this.actionUrls = __WEBPACK_IMPORTED_MODULE_0_lodash___default.a.assign({}, this.actionUrls, {
          listUrl: __WEBPACK_IMPORTED_MODULE_2__common__["a" /* commonUrls */].testTableForTreeInit + '?from=' + from + '&to=' + to
        });
      }
    }
  }
});

/***/ }),
/* 135 */
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
Object.defineProperty(__webpack_exports__, "__esModule", { value: true });
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_0_huodh_vue_plugins__ = __webpack_require__(1);
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_0_huodh_vue_plugins___default = __webpack_require__.n(__WEBPACK_IMPORTED_MODULE_0_huodh_vue_plugins__);
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_1__common__ = __webpack_require__(10);
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_2__data__ = __webpack_require__(111);





var panel = __WEBPACK_IMPORTED_MODULE_0_huodh_vue_plugins___default.a.panel;
var tab = __WEBPACK_IMPORTED_MODULE_0_huodh_vue_plugins___default.a.tab;
var tree = __WEBPACK_IMPORTED_MODULE_0_huodh_vue_plugins___default.a.tree;
/* harmony default export */ __webpack_exports__["default"] = ({
  name: 'tree-example',
  data: function data() {
    return {
      tabData: __WEBPACK_IMPORTED_MODULE_2__data__["a" /* default */],
      treeData: __WEBPACK_IMPORTED_MODULE_1__common__["a" /* commonUrls */].leftTreeDemo
    };
  },

  components: {
    panel: panel, tab: tab, tree: tree
  },
  methods: {
    testClick: function testClick() {
      alert(123);
    }
  }
});

/***/ }),
/* 136 */,
/* 137 */,
/* 138 */,
/* 139 */,
/* 140 */,
/* 141 */,
/* 142 */,
/* 143 */
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
Object.defineProperty(__webpack_exports__, "__esModule", { value: true });
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_0__components_common_Head__ = __webpack_require__(49);
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_0__components_common_Head___default = __webpack_require__.n(__WEBPACK_IMPORTED_MODULE_0__components_common_Head__);
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_1__components_common_Foot__ = __webpack_require__(48);
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_1__components_common_Foot___default = __webpack_require__.n(__WEBPACK_IMPORTED_MODULE_1__components_common_Foot__);
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_2__components_index_Index__ = __webpack_require__(221);
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_2__components_index_Index___default = __webpack_require__.n(__WEBPACK_IMPORTED_MODULE_2__components_index_Index__);





/* harmony default export */ __webpack_exports__["default"] = ({
  components: {
    vhead: __WEBPACK_IMPORTED_MODULE_0__components_common_Head___default.a,
    vfoot: __WEBPACK_IMPORTED_MODULE_1__components_common_Foot___default.a,
    vindex: __WEBPACK_IMPORTED_MODULE_2__components_index_Index___default.a
  }
});

/***/ }),
/* 144 */,
/* 145 */,
/* 146 */,
/* 147 */,
/* 148 */,
/* 149 */,
/* 150 */,
/* 151 */,
/* 152 */,
/* 153 */,
/* 154 */,
/* 155 */,
/* 156 */,
/* 157 */,
/* 158 */,
/* 159 */,
/* 160 */,
/* 161 */,
/* 162 */,
/* 163 */,
/* 164 */,
/* 165 */,
/* 166 */,
/* 167 */,
/* 168 */,
/* 169 */,
/* 170 */,
/* 171 */,
/* 172 */,
/* 173 */,
/* 174 */,
/* 175 */,
/* 176 */,
/* 177 */,
/* 178 */,
/* 179 */,
/* 180 */,
/* 181 */,
/* 182 */,
/* 183 */,
/* 184 */,
/* 185 */,
/* 186 */,
/* 187 */,
/* 188 */,
/* 189 */
/***/ (function(module, exports) {

// removed by extract-text-webpack-plugin

/***/ }),
/* 190 */
/***/ (function(module, exports) {

// removed by extract-text-webpack-plugin

/***/ }),
/* 191 */
/***/ (function(module, exports) {

// removed by extract-text-webpack-plugin

/***/ }),
/* 192 */,
/* 193 */
/***/ (function(module, exports) {

// removed by extract-text-webpack-plugin

/***/ }),
/* 194 */
/***/ (function(module, exports) {

// removed by extract-text-webpack-plugin

/***/ }),
/* 195 */
/***/ (function(module, exports) {

// removed by extract-text-webpack-plugin

/***/ }),
/* 196 */
/***/ (function(module, exports) {

// removed by extract-text-webpack-plugin

/***/ }),
/* 197 */,
/* 198 */
/***/ (function(module, exports) {

// removed by extract-text-webpack-plugin

/***/ }),
/* 199 */,
/* 200 */,
/* 201 */
/***/ (function(module, exports) {

// removed by extract-text-webpack-plugin

/***/ }),
/* 202 */
/***/ (function(module, exports) {

// removed by extract-text-webpack-plugin

/***/ }),
/* 203 */
/***/ (function(module, exports) {

// removed by extract-text-webpack-plugin

/***/ }),
/* 204 */
/***/ (function(module, exports) {

// removed by extract-text-webpack-plugin

/***/ }),
/* 205 */,
/* 206 */
/***/ (function(module, exports) {

// removed by extract-text-webpack-plugin

/***/ }),
/* 207 */,
/* 208 */
/***/ (function(module, exports) {

// removed by extract-text-webpack-plugin

/***/ }),
/* 209 */
/***/ (function(module, exports) {

// removed by extract-text-webpack-plugin

/***/ }),
/* 210 */
/***/ (function(module, exports) {

// removed by extract-text-webpack-plugin

/***/ }),
/* 211 */
/***/ (function(module, exports) {

// removed by extract-text-webpack-plugin

/***/ }),
/* 212 */
/***/ (function(module, exports) {

// removed by extract-text-webpack-plugin

/***/ }),
/* 213 */,
/* 214 */,
/* 215 */
/***/ (function(module, exports) {

// removed by extract-text-webpack-plugin

/***/ }),
/* 216 */
/***/ (function(module, exports) {

// removed by extract-text-webpack-plugin

/***/ }),
/* 217 */
/***/ (function(module, exports) {

// removed by extract-text-webpack-plugin

/***/ }),
/* 218 */,
/* 219 */
/***/ (function(module, exports) {

// removed by extract-text-webpack-plugin

/***/ }),
/* 220 */,
/* 221 */
/***/ (function(module, exports, __webpack_require__) {


/* styles */
__webpack_require__(202)

var Component = __webpack_require__(0)(
  /* script */
  __webpack_require__(115),
  /* template */
  __webpack_require__(263),
  /* scopeId */
  "data-v-5394aa28",
  /* cssModules */
  null
)

module.exports = Component.exports


/***/ }),
/* 222 */
/***/ (function(module, exports, __webpack_require__) {


/* styles */
__webpack_require__(191)

var Component = __webpack_require__(0)(
  /* script */
  __webpack_require__(116),
  /* template */
  __webpack_require__(252),
  /* scopeId */
  "data-v-18295109",
  /* cssModules */
  null
)

module.exports = Component.exports


/***/ }),
/* 223 */
/***/ (function(module, exports, __webpack_require__) {


/* styles */
__webpack_require__(209)

var Component = __webpack_require__(0)(
  /* script */
  __webpack_require__(117),
  /* template */
  __webpack_require__(271),
  /* scopeId */
  "data-v-84c7c60a",
  /* cssModules */
  null
)

module.exports = Component.exports


/***/ }),
/* 224 */
/***/ (function(module, exports, __webpack_require__) {


/* styles */
__webpack_require__(210)

var Component = __webpack_require__(0)(
  /* script */
  __webpack_require__(118),
  /* template */
  __webpack_require__(272),
  /* scopeId */
  "data-v-9336f66e",
  /* cssModules */
  null
)

module.exports = Component.exports


/***/ }),
/* 225 */
/***/ (function(module, exports, __webpack_require__) {


/* styles */
__webpack_require__(204)

var Component = __webpack_require__(0)(
  /* script */
  __webpack_require__(119),
  /* template */
  __webpack_require__(266),
  /* scopeId */
  "data-v-7015e3f3",
  /* cssModules */
  null
)

module.exports = Component.exports


/***/ }),
/* 226 */
/***/ (function(module, exports, __webpack_require__) {


/* styles */
__webpack_require__(208)

var Component = __webpack_require__(0)(
  /* script */
  __webpack_require__(120),
  /* template */
  __webpack_require__(270),
  /* scopeId */
  "data-v-7bbafc49",
  /* cssModules */
  null
)

module.exports = Component.exports


/***/ }),
/* 227 */
/***/ (function(module, exports, __webpack_require__) {


/* styles */
__webpack_require__(189)

var Component = __webpack_require__(0)(
  /* script */
  __webpack_require__(121),
  /* template */
  __webpack_require__(250),
  /* scopeId */
  "data-v-10a49f09",
  /* cssModules */
  null
)

module.exports = Component.exports


/***/ }),
/* 228 */
/***/ (function(module, exports, __webpack_require__) {


/* styles */
__webpack_require__(193)

var Component = __webpack_require__(0)(
  /* script */
  __webpack_require__(122),
  /* template */
  __webpack_require__(254),
  /* scopeId */
  "data-v-234a4bee",
  /* cssModules */
  null
)

module.exports = Component.exports


/***/ }),
/* 229 */
/***/ (function(module, exports, __webpack_require__) {


/* styles */
__webpack_require__(203)

var Component = __webpack_require__(0)(
  /* script */
  __webpack_require__(123),
  /* template */
  __webpack_require__(264),
  /* scopeId */
  "data-v-6251a9c5",
  /* cssModules */
  null
)

module.exports = Component.exports


/***/ }),
/* 230 */
/***/ (function(module, exports, __webpack_require__) {


/* styles */
__webpack_require__(216)

var Component = __webpack_require__(0)(
  /* script */
  __webpack_require__(124),
  /* template */
  __webpack_require__(278),
  /* scopeId */
  "data-v-e2586bfc",
  /* cssModules */
  null
)

module.exports = Component.exports


/***/ }),
/* 231 */
/***/ (function(module, exports, __webpack_require__) {


/* styles */
__webpack_require__(215)

var Component = __webpack_require__(0)(
  /* script */
  __webpack_require__(125),
  /* template */
  __webpack_require__(277),
  /* scopeId */
  "data-v-d264c938",
  /* cssModules */
  null
)

module.exports = Component.exports


/***/ }),
/* 232 */
/***/ (function(module, exports, __webpack_require__) {


/* styles */
__webpack_require__(212)

var Component = __webpack_require__(0)(
  /* script */
  __webpack_require__(126),
  /* template */
  __webpack_require__(274),
  /* scopeId */
  "data-v-b43ca052",
  /* cssModules */
  null
)

module.exports = Component.exports


/***/ }),
/* 233 */
/***/ (function(module, exports, __webpack_require__) {


/* styles */
__webpack_require__(195)

var Component = __webpack_require__(0)(
  /* script */
  __webpack_require__(127),
  /* template */
  __webpack_require__(256),
  /* scopeId */
  "data-v-2c08be36",
  /* cssModules */
  null
)

module.exports = Component.exports


/***/ }),
/* 234 */
/***/ (function(module, exports, __webpack_require__) {


/* styles */
__webpack_require__(211)

var Component = __webpack_require__(0)(
  /* script */
  __webpack_require__(128),
  /* template */
  __webpack_require__(273),
  /* scopeId */
  "data-v-9f39967e",
  /* cssModules */
  null
)

module.exports = Component.exports


/***/ }),
/* 235 */
/***/ (function(module, exports, __webpack_require__) {


/* styles */
__webpack_require__(198)

var Component = __webpack_require__(0)(
  /* script */
  __webpack_require__(129),
  /* template */
  __webpack_require__(259),
  /* scopeId */
  "data-v-36708cc3",
  /* cssModules */
  null
)

module.exports = Component.exports


/***/ }),
/* 236 */
/***/ (function(module, exports, __webpack_require__) {


/* styles */
__webpack_require__(201)

var Component = __webpack_require__(0)(
  /* script */
  __webpack_require__(130),
  /* template */
  __webpack_require__(262),
  /* scopeId */
  "data-v-465a0be2",
  /* cssModules */
  null
)

module.exports = Component.exports


/***/ }),
/* 237 */
/***/ (function(module, exports, __webpack_require__) {


/* styles */
__webpack_require__(196)

var Component = __webpack_require__(0)(
  /* script */
  __webpack_require__(131),
  /* template */
  __webpack_require__(257),
  /* scopeId */
  "data-v-33a03b53",
  /* cssModules */
  null
)

module.exports = Component.exports


/***/ }),
/* 238 */
/***/ (function(module, exports, __webpack_require__) {


/* styles */
__webpack_require__(217)

var Component = __webpack_require__(0)(
  /* script */
  __webpack_require__(132),
  /* template */
  __webpack_require__(279),
  /* scopeId */
  "data-v-e4ead196",
  /* cssModules */
  null
)

module.exports = Component.exports


/***/ }),
/* 239 */
/***/ (function(module, exports, __webpack_require__) {

var Component = __webpack_require__(0)(
  /* script */
  null,
  /* template */
  __webpack_require__(265),
  /* scopeId */
  null,
  /* cssModules */
  null
)

module.exports = Component.exports


/***/ }),
/* 240 */
/***/ (function(module, exports, __webpack_require__) {


/* styles */
__webpack_require__(206)

var Component = __webpack_require__(0)(
  /* script */
  __webpack_require__(133),
  /* template */
  __webpack_require__(268),
  /* scopeId */
  "data-v-725a7567",
  /* cssModules */
  null
)

module.exports = Component.exports


/***/ }),
/* 241 */
/***/ (function(module, exports, __webpack_require__) {


/* styles */
__webpack_require__(219)

var Component = __webpack_require__(0)(
  /* script */
  __webpack_require__(134),
  /* template */
  __webpack_require__(281),
  /* scopeId */
  "data-v-fa2731c2",
  /* cssModules */
  null
)

module.exports = Component.exports


/***/ }),
/* 242 */
/***/ (function(module, exports, __webpack_require__) {


/* styles */
__webpack_require__(190)

var Component = __webpack_require__(0)(
  /* script */
  __webpack_require__(135),
  /* template */
  __webpack_require__(251),
  /* scopeId */
  "data-v-1514f249",
  /* cssModules */
  null
)

module.exports = Component.exports


/***/ }),
/* 243 */,
/* 244 */,
/* 245 */,
/* 246 */,
/* 247 */,
/* 248 */,
/* 249 */,
/* 250 */
/***/ (function(module, exports) {

module.exports={render:function (){var _vm=this;var _h=_vm.$createElement;var _c=_vm._self._c||_h;
  return _c('div', {
    staticClass: "tree"
  }, [_c('panel', [_c('h1', {
    slot: "header"
  }, [_vm._v("Test Editable Tree")]), _vm._v(" "), _c('tree', {
    attrs: {
      "treeData": _vm.treeData,
      "editable": true,
      "actionUrls": _vm.actionUrls
    }
  })], 1), _vm._v(" "), _c('panel', [_c('tab', {
    attrs: {
      "data": _vm.tabData
    }
  }), _vm._v(" "), _c('span', {
    slot: "footer"
  }, [_vm._v("test Editable tree code")])], 1)], 1)
},staticRenderFns: []}

/***/ }),
/* 251 */
/***/ (function(module, exports) {

module.exports={render:function (){var _vm=this;var _h=_vm.$createElement;var _c=_vm._self._c||_h;
  return _c('div', {
    staticClass: "tree"
  }, [_c('panel', [_c('h1', {
    slot: "header"
  }, [_vm._v("Test Tree")]), _vm._v(" "), _c('tree', {
    attrs: {
      "treeData": _vm.treeData
    },
    on: {
      "click": _vm.testClick
    }
  })], 1), _vm._v(" "), _c('panel', [_c('tab', {
    attrs: {
      "data": _vm.tabData
    }
  }), _vm._v(" "), _c('span', {
    slot: "footer"
  }, [_vm._v("test tables")])], 1)], 1)
},staticRenderFns: []}

/***/ }),
/* 252 */
/***/ (function(module, exports) {

module.exports={render:function (){var _vm=this;var _h=_vm.$createElement;var _c=_vm._self._c||_h;
  return _c('div', {
    staticClass: "form-sample"
  }, [_c('panel', [_c('h1', {
    slot: "header"
  }, [_vm._v("Test Form")]), _vm._v(" "), _c('vform', {
    attrs: {
      "id": "actionform",
      "actions": _vm.actions
    }
  })], 1), _vm._v(" "), _c('panel', [_c('tab', {
    attrs: {
      "data": _vm.tabData
    }
  }), _vm._v(" "), _c('span', {
    slot: "footer"
  }, [_vm._v("test form")])], 1)], 1)
},staticRenderFns: []}

/***/ }),
/* 253 */,
/* 254 */
/***/ (function(module, exports) {

module.exports={render:function (){var _vm=this;var _h=_vm.$createElement;var _c=_vm._self._c||_h;
  return _c('div', {
    staticClass: "form-sample"
  }, [_c('panel', [_c('h1', {
    slot: "header"
  }, [_vm._v("Test Form")]), _vm._v(" "), _c('vform', {
    attrs: {
      "id": 1,
      "actionUrls": _vm.actionUrls
    }
  })], 1), _vm._v(" "), _c('panel', [_c('tab', {
    attrs: {
      "data": _vm.tabData
    }
  }), _vm._v(" "), _c('span', {
    slot: "footer"
  }, [_vm._v("test form")])], 1)], 1)
},staticRenderFns: []}

/***/ }),
/* 255 */
/***/ (function(module, exports) {

module.exports={render:function (){var _vm=this;var _h=_vm.$createElement;var _c=_vm._self._c||_h;
  return _c('div', [_c('vhead'), _vm._v(" "), _c('vindex'), _vm._v(" "), _c('vfoot')], 1)
},staticRenderFns: []}

/***/ }),
/* 256 */
/***/ (function(module, exports) {

module.exports={render:function (){var _vm=this;var _h=_vm.$createElement;var _c=_vm._self._c||_h;
  return _c('div', {
    staticClass: "modal-sample"
  }, [_c('panel', {
    attrs: {
      "col": 6
    }
  }, [_c('tab', {
    attrs: {
      "data": _vm.defaultModalData
    }
  }), _vm._v(" "), _c('span', {
    slot: "footer"
  }, [_vm._v("test the default modal")])], 1), _vm._v(" "), _c('panel', {
    attrs: {
      "col": 6
    }
  }, [_c('tab', {
    attrs: {
      "data": _vm.modalNoFooterData
    }
  }), _vm._v(" "), _c('span', {
    slot: "footer"
  }, [_vm._v("test the no footer modal")])], 1), _vm._v(" "), _c('panel', {
    attrs: {
      "col": 6
    }
  }, [_c('tab', {
    attrs: {
      "data": _vm.confirmModalData
    }
  }), _vm._v(" "), _c('span', {
    slot: "footer"
  }, [_vm._v("test the confirm Modal")])], 1), _vm._v(" "), _c('panel', {
    attrs: {
      "col": 6
    }
  }, [_c('tab', {
    attrs: {
      "data": _vm.primaryModalData
    }
  }), _vm._v(" "), _c('span', {
    slot: "footer"
  }, [_vm._v("test the primary Modal")])], 1), _vm._v(" "), _c('panel', {
    attrs: {
      "col": 6
    }
  }, [_c('tab', {
    attrs: {
      "data": _vm.successModalData
    }
  }), _vm._v(" "), _c('span', {
    slot: "footer"
  }, [_vm._v("test the success Modal")])], 1), _vm._v(" "), _c('panel', {
    attrs: {
      "col": 6
    }
  }, [_c('tab', {
    attrs: {
      "data": _vm.infoModalData
    }
  }), _vm._v(" "), _c('span', {
    slot: "footer"
  }, [_vm._v("test the info Modal")])], 1), _vm._v(" "), _c('panel', {
    attrs: {
      "col": 6
    }
  }, [_c('tab', {
    attrs: {
      "data": _vm.dangerModalData
    }
  }), _vm._v(" "), _c('span', {
    slot: "footer"
  }, [_vm._v("test the danger Modal")])], 1)], 1)
},staticRenderFns: []}

/***/ }),
/* 257 */
/***/ (function(module, exports) {

module.exports={render:function (){var _vm=this;var _h=_vm.$createElement;var _c=_vm._self._c||_h;
  return _c('div', {
    staticClass: "panel-sample"
  }, [_c('panel', [_c('h1', {
    slot: "header"
  }, [_vm._v("full width Panel")]), _vm._v(" "), _c('p', [_vm._v("content here")]), _vm._v("\n    [code]\n    "), _c('pre', {
    attrs: {
      "data-src": _vm.$basePath + 'static/demo-data/panel.html'
    }
  }), _vm._v(" "), _c('span', {
    slot: "footer"
  }, [_vm._v("panel desc")])]), _vm._v(" "), _c('div', {
    staticClass: "row"
  }, [_c('panel', {
    attrs: {
      "col": 6
    }
  }, [_c('h1', {
    slot: "header"
  }, [_vm._v("1/2")]), _vm._v(" "), _c('p', [_vm._v("content here")]), _vm._v("\n      [code]\n      "), _c('pre', {
    attrs: {
      "data-src": _vm.$basePath + 'static/demo-data/panel-6.html'
    }
  }), _vm._v(" "), _c('span', {
    slot: "footer"
  }, [_vm._v("panel desc")])]), _vm._v(" "), _c('panel', {
    attrs: {
      "col": 6
    }
  }, [_c('h1', {
    slot: "header"
  }, [_vm._v("2/2")]), _vm._v("\n      这里可以放置其他内容，也可以嵌套其他插件和组件\n      "), _c('span', {
    slot: "footer"
  }, [_vm._v("panel desc")])])], 1), _vm._v(" "), _c('panel', {
    attrs: {
      "col": 4
    }
  }, [_c('h2', {
    slot: "header"
  }, [_vm._v("1/3")]), _vm._v("\n    这里可以放置其他内容，也可以嵌套其他插件和组件\n    "), _c('span', {
    slot: "footer"
  }, [_vm._v("panel desc")])]), _vm._v(" "), _c('panel', {
    attrs: {
      "col": 4
    }
  }, [_c('h2', {
    slot: "header"
  }, [_vm._v("2/3")]), _vm._v("\n    这里可以放置其他内容，也可以嵌套其他插件和组件\n    "), _c('span', {
    slot: "footer"
  }, [_vm._v("panel desc")])]), _vm._v(" "), _c('panel', {
    attrs: {
      "col": 4
    }
  }, [_c('h2', {
    slot: "header"
  }, [_vm._v("3/3")]), _vm._v("\n    这里可以放置其他内容，也可以嵌套其他插件和组件\n    "), _c('span', {
    slot: "footer"
  }, [_vm._v("panel desc")])]), _vm._v(" "), _c('panel', {
    attrs: {
      "col": 3
    }
  }, [_c('h2', {
    slot: "header"
  }, [_vm._v("1/4")]), _vm._v("\n    这里可以放置其他内容，也可以嵌套其他插件和组件\n    "), _c('span', {
    slot: "footer"
  }, [_vm._v("panel desc")])]), _vm._v(" "), _c('panel', {
    attrs: {
      "col": 3
    }
  }, [_c('h2', {
    slot: "header"
  }, [_vm._v("2/4")]), _vm._v("\n    这里可以放置其他内容，也可以嵌套其他插件和组件\n    "), _c('span', {
    slot: "footer"
  }, [_vm._v("panel desc")])]), _vm._v(" "), _c('panel', {
    attrs: {
      "col": 3
    }
  }, [_c('h2', {
    slot: "header"
  }, [_vm._v("3/4")]), _vm._v("\n    这里可以放置其他内容，也可以嵌套其他插件和组件\n    "), _c('span', {
    slot: "footer"
  }, [_vm._v("panel desc")])]), _vm._v(" "), _c('panel', {
    attrs: {
      "col": 3
    }
  }, [_c('h2', {
    slot: "header"
  }, [_vm._v("4/4")]), _vm._v("\n    这里可以放置其他内容，也可以嵌套其他插件和组件\n    "), _c('span', {
    slot: "footer"
  }, [_vm._v("panel desc")])]), _vm._v(" "), _c('panel', {
    attrs: {
      "col": 7
    }
  }, [_c('h2', {
    slot: "header"
  }, [_vm._v("7/12")]), _vm._v("\n    这里可以放置其他内容，也可以嵌套其他插件和组件\n    "), _c('span', {
    slot: "footer"
  }, [_vm._v("panel desc")])]), _vm._v(" "), _c('panel', {
    attrs: {
      "col": 5
    }
  }, [_c('h2', {
    slot: "header"
  }, [_vm._v("5/12")]), _vm._v("\n    这里可以放置其他内容，也可以嵌套其他插件和组件\n    "), _c('span', {
    slot: "footer"
  }, [_vm._v("panel desc")])])], 1)
},staticRenderFns: []}

/***/ }),
/* 258 */,
/* 259 */
/***/ (function(module, exports) {

module.exports={render:function (){var _vm=this;var _h=_vm.$createElement;var _c=_vm._self._c||_h;
  return _c('div', [_c('button', {
    staticClass: "btn btn-primary",
    on: {
      "click": _vm.createDefaultModal
    }
  }, [_vm._v("点击我")])])
},staticRenderFns: []}

/***/ }),
/* 260 */,
/* 261 */,
/* 262 */
/***/ (function(module, exports) {

module.exports={render:function (){var _vm=this;var _h=_vm.$createElement;var _c=_vm._self._c||_h;
  return _c('div', [_c('button', {
    staticClass: "btn btn-success",
    on: {
      "click": _vm.createDefaultModal
    }
  }, [_vm._v("点击我")])])
},staticRenderFns: []}

/***/ }),
/* 263 */
/***/ (function(module, exports) {

module.exports={render:function (){var _vm=this;var _h=_vm.$createElement;var _c=_vm._self._c||_h;
  return _c('div', {
    attrs: {
      "id": "index-app"
    }
  }, [_c('div', {
    staticClass: "left-tree"
  }, [_c('tree', {
    attrs: {
      "treeData": _vm.treeData
    }
  })], 1), _vm._v(" "), _c('div', {
    staticClass: "right-content"
  }, [_c('router-view')], 1)])
},staticRenderFns: []}

/***/ }),
/* 264 */
/***/ (function(module, exports) {

module.exports={render:function (){var _vm=this;var _h=_vm.$createElement;var _c=_vm._self._c||_h;
  return _c('div', [_c('button', {
    staticClass: "btn btn-default",
    on: {
      "click": _vm.createDefaultModal
    }
  }, [_vm._v("点击我")])])
},staticRenderFns: []}

/***/ }),
/* 265 */
/***/ (function(module, exports) {

module.exports={render:function (){var _vm=this;var _h=_vm.$createElement;var _c=_vm._self._c||_h;
  return _c('h2', [_vm._v("外部component")])
},staticRenderFns: []}

/***/ }),
/* 266 */
/***/ (function(module, exports) {

module.exports={render:function (){var _vm=this;var _h=_vm.$createElement;var _c=_vm._self._c||_h;
  return _c('div', {
    staticClass: "dashboard"
  }, [_vm._v("dashboard")])
},staticRenderFns: []}

/***/ }),
/* 267 */,
/* 268 */
/***/ (function(module, exports) {

module.exports={render:function (){var _vm=this;var _h=_vm.$createElement;var _c=_vm._self._c||_h;
  return _c('div', {
    staticClass: "table-sample"
  }, [_c('panel', [_c('h1', {
    slot: "header"
  }, [_vm._v("Test Table")]), _vm._v(" "), _c('vtable', {
    attrs: {
      "id": 1,
      "editable": true,
      "actionUrls": _vm.actionUrls,
      "actions": _vm.actions
    }
  })], 1), _vm._v(" "), _c('panel', [_c('tab', {
    attrs: {
      "data": _vm.tabData
    }
  }), _vm._v(" "), _c('span', {
    slot: "footer"
  }, [_vm._v("test tables")])], 1)], 1)
},staticRenderFns: []}

/***/ }),
/* 269 */,
/* 270 */
/***/ (function(module, exports) {

module.exports={render:function (){var _vm=this;var _h=_vm.$createElement;var _c=_vm._self._c||_h;
  return _c('div', {
    staticClass: "date-sample"
  }, [_c('panel', {
    attrs: {
      "col": 6
    }
  }, [_c('h1', {
    slot: "header"
  }, [_vm._v("Test Date")]), _vm._v(" "), _c('datePicker')], 1), _vm._v(" "), _c('panel', {
    attrs: {
      "col": 6
    }
  }, [_c('h1', {
    slot: "header"
  }, [_vm._v("Test Date With Init")]), _vm._v(" "), _c('datePicker', {
    attrs: {
      "value": _vm.dateInit
    }
  })], 1), _vm._v(" "), _c('panel', {
    attrs: {
      "col": 6
    }
  }, [_c('h1', {
    slot: "header"
  }, [_vm._v("Test Date Range")]), _vm._v(" "), _c('datePicker', {
    attrs: {
      "range": true
    }
  })], 1), _vm._v(" "), _c('panel', {
    attrs: {
      "col": 6
    }
  }, [_c('h1', {
    slot: "header"
  }, [_vm._v("Test Date Range With Init")]), _vm._v(" "), _c('datePicker', {
    attrs: {
      "range": true,
      "value": _vm.dateRangeInit
    }
  })], 1), _vm._v(" "), _c('panel', {
    attrs: {
      "col": 6
    }
  }, [_c('h1', {
    slot: "header"
  }, [_vm._v("Test Date In English")]), _vm._v(" "), _c('datePicker', {
    attrs: {
      "language": "en"
    }
  })], 1), _vm._v(" "), _c('panel', [_c('tab', {
    attrs: {
      "data": _vm.dateData
    }
  }), _vm._v(" "), _c('span', {
    slot: "footer"
  }, [_vm._v("test date")])], 1)], 1)
},staticRenderFns: []}

/***/ }),
/* 271 */
/***/ (function(module, exports) {

module.exports={render:function (){var _vm=this;var _h=_vm.$createElement;var _c=_vm._self._c||_h;
  return _c('div', {
    staticClass: "table-sample"
  }, [_c('panel', [_c('h1', {
    slot: "header"
  }, [_vm._v("Test Table")]), _vm._v(" "), _c('vtable', {
    attrs: {
      "id": 1,
      "editable": true,
      "actionUrls": _vm.actionUrls,
      "actions": _vm.actions
    }
  })], 1), _vm._v(" "), _c('panel', [_c('tab', {
    attrs: {
      "data": _vm.tabData
    }
  }), _vm._v(" "), _c('span', {
    slot: "footer"
  }, [_vm._v("test tables")])], 1)], 1)
},staticRenderFns: []}

/***/ }),
/* 272 */
/***/ (function(module, exports) {

module.exports={render:function (){var _vm=this;var _h=_vm.$createElement;var _c=_vm._self._c||_h;
  return _c('div', {
    staticClass: "tree"
  }, [_c('panel', [_c('h1', {
    slot: "header"
  }, [_vm._v("Test Editable Tree")]), _vm._v(" "), _c('tree', {
    attrs: {
      "editable": true,
      "actions": _vm.actions
    }
  })], 1), _vm._v(" "), _c('panel', [_c('tab', {
    attrs: {
      "data": _vm.tabData
    }
  }), _vm._v(" "), _c('span', {
    slot: "footer"
  }, [_vm._v("test Editable tree code")])], 1)], 1)
},staticRenderFns: []}

/***/ }),
/* 273 */
/***/ (function(module, exports) {

module.exports={render:function (){var _vm=this;var _h=_vm.$createElement;var _c=_vm._self._c||_h;
  return _c('div', [_c('button', {
    staticClass: "btn btn-default",
    on: {
      "click": _vm.createDefaultModal
    }
  }, [_vm._v("点击我")])])
},staticRenderFns: []}

/***/ }),
/* 274 */
/***/ (function(module, exports) {

module.exports={render:function (){var _vm=this;var _h=_vm.$createElement;var _c=_vm._self._c||_h;
  return _c('div', [_c('button', {
    staticClass: "btn btn-info",
    on: {
      "click": _vm.createDefaultModal
    }
  }, [_vm._v("点击我")])])
},staticRenderFns: []}

/***/ }),
/* 275 */,
/* 276 */,
/* 277 */
/***/ (function(module, exports) {

module.exports={render:function (){var _vm=this;var _h=_vm.$createElement;var _c=_vm._self._c||_h;
  return _c('div', [_c('button', {
    staticClass: "btn btn-default",
    on: {
      "click": _vm.createDefaultModal
    }
  }, [_vm._v("点击我")])])
},staticRenderFns: []}

/***/ }),
/* 278 */
/***/ (function(module, exports) {

module.exports={render:function (){var _vm=this;var _h=_vm.$createElement;var _c=_vm._self._c||_h;
  return _c('div', [_c('button', {
    staticClass: "btn btn-danger",
    on: {
      "click": _vm.createDefaultModal
    }
  }, [_vm._v("点击我")])])
},staticRenderFns: []}

/***/ }),
/* 279 */
/***/ (function(module, exports) {

module.exports={render:function (){var _vm=this;var _h=_vm.$createElement;var _c=_vm._self._c||_h;
  return _c('div', {
    staticClass: "tab-sample"
  }, [_c('panel', [_c('tab', {
    attrs: {
      "data": _vm.tabData
    }
  }), _vm._v(" "), _c('span', {
    slot: "footer"
  }, [_vm._v("test the tabs")])], 1)], 1)
},staticRenderFns: []}

/***/ }),
/* 280 */,
/* 281 */
/***/ (function(module, exports) {

module.exports={render:function (){var _vm=this;var _h=_vm.$createElement;var _c=_vm._self._c||_h;
  return _c('div', {
    staticClass: "table-sample"
  }, [_c('panel', {
    attrs: {
      "col": "8"
    }
  }, [_c('h1', {
    slot: "header"
  }, [_vm._v("左侧表")]), _vm._v(" "), _c('vtable', {
    attrs: {
      "id": "left_table",
      "actionUrls": _vm.actionUrls
    }
  })], 1), _vm._v(" "), _c('panel', {
    attrs: {
      "col": "4"
    }
  }, [_c('h1', {
    slot: "header"
  }, [_vm._v("右侧树")]), _vm._v(" "), _c('tree', {
    attrs: {
      "treeData": _vm.treeData
    },
    on: {
      "click": _vm.changeTableData
    }
  })], 1), _vm._v(" "), _c('panel', [_c('tab', {
    attrs: {
      "data": _vm.tabData
    }
  }), _vm._v(" "), _c('span', {
    slot: "footer"
  }, [_vm._v("test tables")])], 1)], 1)
},staticRenderFns: []}

/***/ })
],[112]);
//# sourceMappingURL=index.bcdd51b8d2d39d1b2638.js.map