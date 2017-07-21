<template>
  <div class="table-sample">
    <panel>
      <h1 slot="header">Test Table</h1>
      <vtable :id="1" :actionUrls="actionUrls"></vtable>
    </panel>
    <panel>
      <tab :data="tabData"/>
      <span slot="footer">test tables</span>
    </panel>
  </div>
</template>

<script>
  import huodhVuePlugins from 'huodh-vue-plugins'
  import {commonUrls} from '../../common'
  import data from './data'
  let panel = huodhVuePlugins.panel
  let tab = huodhVuePlugins.tab
  let vtable = huodhVuePlugins.vtable
  export default{
    data () {
      return {
        tabData: data,
        actionUrls: {
          listUrl: commonUrls.testTableInit,
          addUrl: commonUrls.vuerouter.testForm,
          detailUrl: commonUrls.testTableInit,
          deleteUrl: commonUrls.testTableRowDel,
          infoUrl: commonUrls.testTableInit
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
