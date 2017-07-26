<template>
  <div class="date-sample">
    <panel :col="6">
      <h1 slot="header">Test Date</h1>
      <datePicker/>
    </panel>
    <panel :col="6">
      <h1 slot="header">Test Date With Init</h1>
      <datePicker :value="dateInit"/>
    </panel>
    <panel :col="6">
      <h1 slot="header">Test Date Range</h1>
      <datePicker :range="true"/>
    </panel>
    <panel :col="6">
      <h1 slot="header">Test Date Range With Init</h1>
      <datePicker :range="true" :value="dateRangeInit"/>
    </panel>
    <panel :col="6">
      <h1 slot="header">Test Date In English</h1>
      <datePicker language="en"/>
    </panel>
    <panel>
      <tab :data="dateData"/>
      <span slot="footer">test date</span>
    </panel>
  </div>
</template>

<script>
  import huodhVuePlugins from 'huodh-vue-plugins'
  import data from './data'
  let panel = huodhVuePlugins.panel
  let tab = huodhVuePlugins.tab
  let vform = huodhVuePlugins.vform
  let datePicker = huodhVuePlugins.datePicker
  export default{
    data () {
      console.log(new Date('2017-03-11').getTime())
      console.log(new Date('2017-04-11').getTime())
      return {
        dateData: data,
        dateInit: 1489190400000,
        dateRangeInit: [1489190400000, 1491868800000]
      }
    },
    components: {
      panel, tab, vform, datePicker
    }
  }
</script>

<style scoped>

</style>
