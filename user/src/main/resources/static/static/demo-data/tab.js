import TestSample from './TestSample.vue'
export default [
  {
    'title': 'tab1',
    'content': 'just string content',
    'show': true
  },
  {
    'title': 'tab2',
    'content': {
      template: '<h2>测试,inline component</h2>'
    },
    'show': false
  },
  {
    'title': 'tab3',
    'content': TestSample,
    'show': false
  },
  {
    'title': 'tab4',
    'content': resolve => {
      setTimeout(() => {
        resolve({
          template: '<h2>异步组件</h2>'
        })
      }, 3000)
    },
    'show': false
  }
]
