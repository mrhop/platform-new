<template>
  <div class="form-sample">
    <panel>
      <h1 slot="header">Test Form</h1>
      <vform id="actionform" :actions="actions"></vform>
    </panel>
    <panel>
      <tab :data="tabData"/>
      <span slot="footer">test form</span>
    </panel>
  </div>
</template>

<script>
  import huodhVuePlugins from 'huodh-vue-plugins'
  import data from './data'
  let panel = huodhVuePlugins.panel
  let tab = huodhVuePlugins.tab
  let vform = huodhVuePlugins.vform
  let formLineAdd = {add1: false, add2: false}
  export default{
    data () {
      return {
        tabData: data,
        actions: {
          init: function (params) {
            if (params.key) {
              console.log('get data by key:' + params.key)
            }
            console.log('deal with the init data by this function itself')
            return {
              'rules': {
                'items': [
                  {
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
                  },
                  {
                    'name': 'testPassword',
                    'label': '测试Password',
                    'type': 'password'
                  },
                  {
                    'name': 'testNumber',
                    'label': '测试Number',
                    'type': 'number',
                    'validate': [{
                      'errorMsg': '不能为空',
                      'regex': '^\\S+$'
                    }]
                  },
                  {
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
                  },
                  {
                    'name': 'testDate',
                    'label': '测试日期',
                    'type': 'date',
                    'validate': [
                      {
                        'errorMsg': '不能为空',
                        'regex': '^\\S+$'
                      }
                    ]
                  },
                  {
                    'name': 'testDateRange',
                    'label': '测试范围日期',
                    'type': 'daterange',
                    'validate': [
                      {
                        'errorMsg': '不能为空',
                        'regex': '^\\S+$'
                      }
                    ]
                  },
                  {
                    'name': 'testSelect',
                    'label': '测试select',
                    'type': 'select',
                    'validate': [{
                      'errorMsg': '不能为空',
                      'regex': '^\\S+$'
                    }],
                    'defaultValue': '2',
                    'items': [
                      {
                        'label': '测试1',
                        'value': '1'
                      },
                      {
                        'label': '测试2',
                        'value': '2'
                      },
                      {
                        'label': '测试3',
                        'value': '3'
                      }
                    ]
                  },
                  {
                    'name': 'testRadio',
                    'label': '测试Radio',
                    'type': 'radio',
                    'validate': [{
                      'errorMsg': '不能为空',
                      'regex': '^\\S+$'
                    }],
                    'defaultValue': '1',
                    'items': [
                      {
                        'label': '测试1',
                        'value': '1'
                      },
                      {
                        'label': '测试2',
                        'value': '2'
                      },
                      {
                        'label': '测试3',
                        'value': '3'
                      }
                    ],
                    'ruleChange': true
                  },
                  {
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
                  },
                  {
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
                  },
                  {
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
                  },
                  {
                    'name': 'testCheckbox',
                    'label': '测试Checkbox',
                    'type': 'checkbox',
                    'validate': [{
                      'errorMsg': '不能为空',
                      'regex': '^\\S+$'
                    }],
                    'defaultValue': [
                      '1',
                      '2'
                    ],
                    'items': [
                      {
                        'label': '测试1',
                        'value': '1'
                      },
                      {
                        'label': '测试2',
                        'value': '2'
                      },
                      {
                        'label': '测试3',
                        'value': '3'
                      }
                    ]
                  },
                  {
                    'name': 'testTree',
                    'label': '下拉树控件',
                    'type': 'tree',
                    'treeData': '/static/demo-data/form/tree.json',
                    'defaultLabel': '测试3',
                    'defaultValue': '3',
                    'validate': [
                      {
                        'errorMsg': '不能为空',
                        'regex': '^\\S+$'
                      }
                    ],
                    'placeholder': '点击选择树'
                  },
                  {
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
                  },
                  [
                    {
                      'name': 'testInline1',
                      'label': '测试Inline1',
                      'type': 'text',
                      'validate': [
                        {
                          'errorMsg': '不能为空',
                          'regex': '^\\S+$'
                        }
                      ]
                    },
                    {
                      'name': 'testInline2',
                      'label': '测试Inline2',
                      'type': 'text'
                    },
                    {
                      'name': 'addLine',
                      'label': '添加新行',
                      'defaultValue': '添加新行',
                      'type': 'button',
                      'ruleChange': true
                    }
                  ],
                  [
                    {
                      'name': 'testInline4',
                      'label': '测试Inline4',
                      'type': 'text',
                      'validate': [
                        {
                          'errorMsg': '不能为空',
                          'regex': '^\\S+$'
                        }
                      ],
                      'hidden': true
                    },
                    {
                      'name': 'testInline5',
                      'label': '测试Inline5',
                      'type': 'text',
                      'hidden': true
                    },
                    {
                      'name': 'delLine1',
                      'label': '删除本行',
                      'defaultValue': '删除本行',
                      'type': 'button',
                      'ruleChange': true,
                      'hidden': true
                    }
                  ],
                  [
                    {
                      'name': 'testInline6',
                      'label': '测试Inline6',
                      'type': 'text',
                      'validate': [
                        {
                          'errorMsg': '不能为空',
                          'regex': '^\\S+$'
                        }
                      ],
                      'hidden': true
                    },
                    {
                      'name': 'testInline7',
                      'label': '测试Inline7',
                      'type': 'text',
                      'hidden': true
                    },
                    {
                      'name': 'delLine2',
                      'label': '删除本行',
                      'defaultValue': '删除本行',
                      'type': 'button',
                      'ruleChange': true,
                      'hidden': true
                    }
                  ]
                ],
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
            }
          },
          save: function (params) {
            if (params.key) {
              console.log('save data by key:' + params.key)
            }
            if (params.data) {
              console.log('these data will be saved:' + JSON.stringify(params.data))
            }
            if (params.multipart) {
              console.log('we shall transfer these data to server by multipart type')
            }
            console.log('deal with the data by this save function itself')
            return {
              success: {
                title: '保存数据',
                message: '保存成功'
              }
            }
          },
          ruleChange: function (params) {
            if (params.parameters) {
              console.log('change rule data by key:' + JSON.stringify(params.data))
            }
            const data = params.parameters
            if (data && data.testRadio) {
              if (data.testRadio === '1') {
                return [
                  {
                    'name': 'ruleChange1',
                    'hidden': false
                  }, {
                    'name': 'ruleChange2',
                    'hidden': true
                  }, {
                    'name': 'ruleChange3',
                    'hidden': true
                  }
                ]
              } else if (data.testRadio === '2') {
                return [
                  {
                    'name': 'ruleChange1',
                    'hidden': true
                  }, {
                    'name': 'ruleChange2',
                    'hidden': false
                  }, {
                    'name': 'ruleChange3',
                    'hidden': true
                  }
                ]
              } else if (data.testRadio === '3') {
                return [
                  {
                    'name': 'ruleChange1',
                    'hidden': true
                  },
                  {
                    'name': 'ruleChange2',
                    'hidden': true
                  },
                  {
                    'name': 'ruleChange3',
                    'defaultValue': '联动测试3' + new Date().getMilliseconds(),
                    'hidden': false
                  }
                ]
              }
            }
            if (data && data.addLine) {
              // add line
              if (!formLineAdd.add1) {
                formLineAdd.add1 = true
                return [
                  {
                    'name': 'testInline4',
                    'hidden': false
                  }, {
                    'name': 'testInline5',
                    'hidden': false
                  }, {
                    'name': 'delLine1',
                    'hidden': false
                  }
                ]
              } else if (!formLineAdd.add2) {
                formLineAdd.add2 = true
                return [
                  {
                    'name': 'testInline6',
                    'hidden': false
                  }, {
                    'name': 'testInline7',
                    'hidden': false
                  }, {
                    'name': 'delLine2',
                    'hidden': false
                  }
                ]
              }
            }
            if (data && data.delLine1) {
              formLineAdd.add1 = false
              return [
                {
                  'name': 'testInline4',
                  'hidden': true
                }, {
                  'name': 'testInline5',
                  'hidden': true
                }, {
                  'name': 'delLine1',
                  'hidden': true
                }
              ]
            }
            if (data && data.delLine2) {
              formLineAdd.add2 = false
              return [
                {
                  'name': 'testInline6',
                  'hidden': true
                }, {
                  'name': 'testInline7',
                  'hidden': true
                }, {
                  'name': 'delLine2',
                  'hidden': true
                }
              ]
            }
          },
          reset: function (params) {
            if (params.key) {
              console.log('reset form by key:' + params.key)
            }
            formLineAdd = {add1: false, add2: false}
          },
          sampleTest: function (params) {
            if (params.key) {
              console.log('reset form by key:' + params.key)
            }
            console.log('right be here')
          }
        }
      }
    },
    components: {
      panel, tab, vform
    }
  }
</script>

<style scoped>

</style>
