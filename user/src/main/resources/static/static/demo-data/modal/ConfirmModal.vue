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
          showFooter: true,
          content: 'just test a single line',
          isConfirmModal: true
        }
      }
    },
    methods: {
      createDefaultModal () {
        this.$Vue.createModal({modalData: this.dataDefault, confirmCallback: this.triggerConfirm.bind(this)})
      },
      triggerConfirm () {
        console.log('you have clicked the confirm button')
      }
    }
  }
</script>

<style rel="stylesheet/scss" lang="scss" scoped>
  button {
    margin: 15px;
  }
</style>
