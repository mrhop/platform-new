<template>
  <div class="tree">
    <panel>
      <h1 slot="header">Test Tree</h1>
      <tree :treeData="treeData" v-on:click="testClick"/>
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
  let tree = huodhVuePlugins.tree
  export default {
    name: 'tree-example',
    data () {
      return {
        tabData: data,
        treeData: commonUrls.leftTreeDemo
      }
    },
    components: {
      panel, tab, tree
    },
    methods: {
      testClick: function () {
        alert(123)
      }
    }
  }
</script>

<style scoped>

</style>
