<template>
  <div>
    <button class="btn btn-success" @click="createDefaultModal">点击我</button>
  </div>
</template>

<script>
  export default{
    data () {
      return {
        dataDefault: {
          type: 'success',
          header: 'Success Modal',
          showFooter: true,
          content: 'just test a single line'
        }
      }
    },
    methods: {
      createDefaultModal () {
        this.$Vue.createModal({modalData: this.dataDefault})
      }
    }
  }
</script>

<style rel="stylesheet/scss" lang="scss" scoped>
  button {
    margin: 15px;
  }
</style>
