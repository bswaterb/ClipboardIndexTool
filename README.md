# ClipboardIndexTool
划词索引工具    A Simple Tool For Indexing By CTRL+C 

###项目说明
这是一个基于监控当前系统剪切板的内容索引工具，你可以复制一些关键词，然后通过该程序将关该键词对应的内容查找出来。
项目基于maven进行构建，目前仍处于初期开发阶段，如果你有好的意见，请提交你的Pull Request。

###结构说明
src/main/java/util目录下有一个util2文件夹，里面的内容和util中的内容有些许不同
（util2为目前的Code主函数服务，util1为正在开发中的GUI界面服务）

###开发进度
Code.java可正常启动运行，使用前需部署对应mysql服务器，数据库框架使用mybatis
GUI.java也可正常启动，使用方式和C大同小异，但目前无法实现类似Code.java的弹窗提醒形式，暂时不可用


**项目组件参考：**
[1.剪切板操作功能 ](https://blog.csdn.net/xietansheng/article/details/70478266 "1.剪切板操作功能 ")
