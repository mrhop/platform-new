<template>
  <div class="form-sample">
    <panel>
      <h1 slot="header">Test Form</h1>
      <vform :id="1" :actionUrls="actionUrls"></vform>
    </panel>
    <panel>
      <tab :data="tabData"/>
      <span slot="footer">test form</span>
    </panel>
  </div>
</template>

<script>
  import huodhVuePlugins from 'huodh-vue-plugins'
  import {commonUrls} from '../../common'
  import data from './data'
  let panel = huodhVuePlugins.panel
  let tab = huodhVuePlugins.tab
  let vform = huodhVuePlugins.vform
  export default{
    data () {
      return {
        tabData: data,
        actionUrls: {
          initUrl: commonUrls.testFormInit,
          ruleChangeUrl: commonUrls.testFormRuleChange,
          saveUrl: commonUrls.testFormSave,
          resetUrl: commonUrls.testFormReset,
          backupUrl: commonUrls.vuerouter.testTable
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
