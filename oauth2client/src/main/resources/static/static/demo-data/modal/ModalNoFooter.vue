<template>
  <div>
    <button class="btn btn-default" @click="createDefaultModal">点击我</button>
  </div>
</template>

<script>
  export default{
    data () {
      return {
        dataDefault: {
          header: 'default Modal',
          showFooter: false,
          content: '没有底部的modal，用于嵌入其他不需要footer的module'
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
