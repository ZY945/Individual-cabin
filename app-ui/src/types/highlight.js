import hljs from 'highlight.js/lib/core'
import javascript from 'highlight.js/lib/languages/javascript'
import 'highlight.js/styles/default.css' // 导入样式表

// 将要使用的语言定义添加到 hljs 对象中
hljs.registerLanguage('javascript', javascript)
