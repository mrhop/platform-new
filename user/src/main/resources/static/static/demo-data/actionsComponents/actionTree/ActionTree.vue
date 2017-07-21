<template>
  <div class="tree">
    <panel>
      <h1 slot="header">Test Editable Tree</h1>
      <tree :editable="true" :actions="actions"/>
    </panel>
    <panel>
      <tab :data="tabData"/>
      <span slot="footer">test Editable tree code</span>
    </panel>
  </div>
</template>

<script>
  import huodhVuePlugins from 'huodh-vue-plugins'
  import data from './data'
  let panel = huodhVuePlugins.panel
  let tab = huodhVuePlugins.tab
  let tree = huodhVuePlugins.tree
  export default {
    name: 'tree-example',
    data () {
      return {
        tabData: data,
        actions: {
          tree: function () {
            return [
              {
                'id': 1,
                'title': 'Sample1',
                'iconClass': 'glyphicon glyphicon-th-large',
                'deletable': false,
                'children': [
                  {
                    'id': 2,
                    'title': 'Panel'
                  },
                  {
                    'id': 3,
                    'title': 'Tab'
                  },
                  {
                    'id': 4,
                    'title': 'Modal'
                  }
                ]
              },
              {
                'id': 5,
                'title': 'Sample2',
                'iconClass': 'glyphicon glyphicon-th-large',
                'children': [
                  {
                    'id': 6,
                    'title': 'Form',
                    'iconClass': 'glyphicon glyphicon-th-large',
                    'children': [
                      {
                        'id': 7,
                        'title': 'Tree'
                      },
                      {
                        'id': 8,
                        'title': 'Date'
                      },
                      {
                        'id': 9,
                        'title': 'TestClick'
                      },
                      {
                        'id': 10,
                        'title': 'OnlyText'
                      }
                    ]
                  },
                  {
                    'id': 11,
                    'title': 'Tree'
                  },
                  {
                    'id': 12,
                    'title': 'Date'
                  },
                  {
                    'id': 13,
                    'title': 'TestClick'
                  },
                  {
                    'id': 14,
                    'title': 'OnlyText'
                  }
                ]
              }
            ]
          },
          add: function (params) {
            if (params.key) {
              console.log('save data by key:' + params.key)
            }
            if (params.data) {
              console.log('these data will be saved:' + JSON.stringify(params.data))
            }
            if (params.multipart) {
              console.log('we shall transfer these data to server by multipart type')
            }
            console.log('save the tree data by this save function itself')
          },
          edit: function (params) {
            if (params.key) {
              console.log('save data by key:' + params.key)
            }
            if (params.data) {
              console.log('these data will be saved:' + JSON.stringify(params.data))
            }
            if (params.multipart) {
              console.log('we shall transfer these data to server by multipart type')
            }
            console.log('update the tree data by this save function itself')
          },
          delete: function (params) {
            console.log('delete tree item with id:' + params.id)
          }
        }
      }
    },
    components: {
      panel, tab, tree
    },
    methods: {}
  }
</script>

<style scoped>

</style>
