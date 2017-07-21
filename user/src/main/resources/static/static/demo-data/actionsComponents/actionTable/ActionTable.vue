<template>
  <div class="table-sample">
    <panel>
      <h1 slot="header">Test Table</h1>
      <vtable :id="1" :editable="true" :actionUrls="actionUrls" :actions="actions"></vtable>
    </panel>
    <panel>
      <tab :data="tabData"/>
      <span slot="footer">test tables</span>
    </panel>
  </div>
</template>

<script>
  import lodash from 'lodash'

  import huodhVuePlugins from 'huodh-vue-plugins'
  import {commonUrls} from '../../common'
  import data from './data'
  let panel = huodhVuePlugins.panel
  let tab = huodhVuePlugins.tab
  let vtable = huodhVuePlugins.vtable
  let tableData = [
    {
      'key': 1,
      'value': [
        '小一',
        [1, 2],
        1,
        '',
        1493568000000
      ]
    },
    {
      'key': 2,
      'value': [
        '小二',
        '',
        '',
        '',
        1493568000000
      ]
    },
    {
      'key': 3,
      'value': [
        '小3',
        '',
        '',
        '',
        1493568000000
      ]
    },
    {
      'key': 4,
      'value': [
        '小4',
        '',
        '',
        '',
        1493568000000
      ]
    },
    {
      'key': 5,
      'value': [
        '小5',
        '',
        '',
        '',
        1493568000000
      ]
    },
    {
      'key': 6,
      'value': [
        '小6',
        '',
        '',
        '',
        1493568000000
      ]
    },
    {
      'key': 7,
      'value': [
        '小7',
        '',
        '',
        '',
        1493568000000
      ]
    },
    {
      'key': 8,
      'value': [
        '小8',
        '',
        '',
        '',
        1493568000000
      ]
    },
    {
      'key': 9,
      'value': [
        '小9',
        '',
        '',
        '',
        1493568000000
      ]
    },
    {
      'key': 10,
      'value': [
        '小10',
        '',
        '',
        '',
        1493568000000
      ]
    },
    {
      'key': 11,
      'value': [
        '小1一',
        '',
        '',
        '',
        1493568000000
      ]
    },
    {
      'key': 12,
      'value': [
        '小1二',
        '',
        '',
        '',
        1493568000000
      ]
    },
    {
      'key': 13,
      'value': [
        '小13',
        '',
        '',
        '',
        1493568000000
      ]
    },
    {
      'key': 14,
      'value': [
        '小14',
        '',
        '',
        '',
        1493568000000
      ]
    },
    {
      'key': 15,
      'value': [
        '小15',
        '',
        '',
        '',
        1493568000000
      ]
    },
    {
      'key': 16,
      'value': [
        '小16',
        '',
        '',
        '',
        1493568000000
      ]
    },
    {
      'key': 17,
      'value': [
        '小17',
        '',
        '',
        '',
        1493568000000
      ]
    },
    {
      'key': 18,
      'value': [
        '小18',
        '',
        '',
        '',
        1493568000000
      ]
    },
    {
      'key': 19,
      'value': [
        '小19',
        '',
        '',
        '',
        1493568000000
      ]
    },
    {
      'key': 20,
      'value': [
        '小20',
        '',
        '',
        '',
        1493568000000
      ]
    },
    {
      'key': 21,
      'value': [
        '小2一',
        '',
        '',
        '',
        1493568000000
      ]
    },
    {
      'key': 22,
      'value': [
        '小2二',
        '',
        '',
        '',
        1493568000000
      ]
    },
    {
      'key': 23,
      'value': [
        '小23',
        '',
        '',
        '',
        1493568000000
      ]
    }
  ]
  export default{
    data () {
      return {
        tabData: data,
        actionUrls: {
          addUrl: commonUrls.vuerouter.testForm,
          detailUrl: commonUrls.testTableInit,
          infoUrl: commonUrls.testTableInit
        },
        actions: {
          list: function (args) {
            console.log('do get list by actions list function')
            var pager = args.pager
            var init = args.init
            var filters = args.filters
            var sorts = args.sorts
            var subData = tableData
            if (filters && filters.name) {
              subData = lodash.filter(tableData, function (o) {
                return o.value[0].indexOf(filters.name) > -1
              })
            }
            if (filters && filters.testDate) {
              subData = lodash.filter(subData, function (o) {
                return (+o.value[4]) < filters.testDate
              })
            }
            if (filters && filters.testSelect) {
              subData = lodash.filter(subData, function (o) {
                return (+o.value[2]) === filters.testSelect
              })
            }
            if (sorts && sorts.name) {
              lodash.reverse(subData)
            }
            if ((pager.currentPage - 1) * pager.pageSize >= subData.length) {
              pager.currentPage = Math.ceil(subData.length / pager.pageSize)
              if (pager.currentPage === 0) {
                pager.currentPage = 1
              }
            }
            var returnData = subData.slice((pager.currentPage - 1) * pager.pageSize, pager.currentPage * pager.pageSize)
            if (init) {
              // 初始化
              return {
                'rules': {
                  'header': [
                    {
                      'name': '#sn',
                      'title': '#sn'
                    },
                    {
                      'name': 'name',
                      'title': '名称',
                      'type': 'text',
                      'editable': true,
                      'filter': true,
                      'sortable': true
                    },
                    {
                      'name': 'testCheckbox',
                      'title': '测试多选',
                      'type': 'checkbox',
                      'editable': true,
                      'items': [
                        {
                          'label': '测试1',
                          'value': 1
                        },
                        {
                          'label': '测试2',
                          'value': 2
                        },
                        {
                          'label': '测试3',
                          'value': 3
                        }
                      ]
                    },
                    {
                      'name': 'testSelect',
                      'title': '测试select',
                      'type': 'select',
                      'editable': true,
                      'filter': true,
                      'items': [
                        {
                          'label': '测试1',
                          'value': 1
                        },
                        {
                          'label': '测试2',
                          'value': 2
                        },
                        {
                          'label': '测试3',
                          'value': 3
                        }
                      ]
                    },
                    {
                      'name': 'testImg',
                      'title': '测试图片',
                      'type': 'image',
                      'editable': true
                    },
                    {
                      'name': 'testDate',
                      'title': '在此之前',
                      'editable': true,
                      'type': 'date',
                      'filter': true
                    }
                  ],
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
                  pager
                }
              }
            } else {
              return {
                'data': {
                  'rows': returnData,
                  'totalCount': subData.length,
                  pager,
                  filters,
                  sorts
                }
              }
            }
          },
          delete: function (args) {
            console.log('do delete by actions delete function')
            var id = parseInt(args.key)
            lodash.remove(tableData, function (o) {
              return o.key === id
            })
          },
          edit: function (args) {
            console.log('this is for editing')
            if (args && args.headerItem && (args.headerItem.type === 'file' || args.headerItem.type === 'image')) {
              return 'http://www.hopever.cn/mogilefs/images/user/photo/14817789496788475104059462733375755.jpg'
            }
          }
        }
      }
    },
    components: {
      panel, tab, vtable
    }
  }
</script>

<style scoped>

</style>
