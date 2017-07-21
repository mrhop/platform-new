<template>
  <div class="table-sample">
    <panel col="8">
      <h1 slot="header">左侧表</h1>
      <vtable id="left_table" :actionUrls="actionUrls"></vtable>
    </panel>
    <panel col="4">
      <h1 slot="header">右侧树</h1>
      <tree :treeData="treeData" v-on:click="changeTableData"/>
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
  let tree = huodhVuePlugins.tree
  export default{
    data () {
      return {
        tabData: data,
        treeData: commonUrls.testTreeWithTableInit,
        actionUrls: {
          listUrl: commonUrls.testTableForTreeInit
        }
      }
    },
    components: {
      panel, tab, vtable, tree
    },
    methods: {
      changeTableData: function (limit) {
        const {from = 0, to = 0} = limit || {}
        if (!from) {
          // all data
          this.actionUrls = lodash.assign({}, this.actionUrls, {
            listUrl: commonUrls.testTableForTreeInit
          })
        } else if (from && !to) {
          this.actionUrls = lodash.assign({}, this.actionUrls, {
            listUrl: commonUrls.testTableForTreeInit + '?from=' + from
          })
        } else {
          this.actionUrls = lodash.assign({}, this.actionUrls, {
            listUrl: commonUrls.testTableForTreeInit + '?from=' + from + '&to=' + to
          })
        }
      }
    }
  }
</script>

<style scoped>

</style>
