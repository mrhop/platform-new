<template>
  <div class="tree">
    <panel>
      <h1 slot="header">Test Editable Tree</h1>
      <tree :treeData="treeData" :editable="true" :actionUrls="actionUrls"/>
    </panel>
    <panel>
      <tab :data="tabData"/>
      <span slot="footer">test Editable tree code</span>
    </panel>
  </div>
</template>

<script>
  import huodhVuePlugins from 'huodh-vue-plugins'
  import {commonUrls} from '../../common'
  import data from './data'
  let panel = huodhVuePlugins.panel
  let tab = huodhVuePlugins.tab
  let tree = huodhVuePlugins.tree
  export default {
    name: 'tree-example',
    data () {
      return {
        tabData: data,
        treeData: commonUrls.testEditTreeInit,
        actionUrls: {
          addUrl: commonUrls.testEditTreeSave,
          editUrl: commonUrls.testEditTreeUpdate,
          deleteUrl: commonUrls.testEditTreeDelete
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
