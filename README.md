# ClipboardIndexTool
划词索引工具    A Simple Tool For Indexing By CTRL+C 

### 项目说明

这是一个基于监控当前系统剪切板的内容索引工具，你可以复制一些关键词，然后通过该程序将关该键词对应的内容查找出来。
项目基于maven进行构建，数据库框架使用mybatis，目前仍处于初期开发阶段，如果你有好的意见，请提交你的Pull Request。

目前项目停止开发客户端GUI界面

### 使用说明 beta v0.3版本 (ps:需要有java以及mysql操作基础）

1.你需要拥有一个mysql数据库，并创建一个内含两个属性的表（一个属性为问题，另一个属性为该问题对应的索引结果）

2.向该表中录入你需要的数据，并将数据库连接信息输入到  /src/main/resources/config.properties 中

3.修改并根据自己的需要决定是否重写 /src/main/resources/mapper/ExamMapper.xml 

4.运行Main.java（命令行运行或IDE内运行）

5.程序在运行时会监控您的剪切板，当内容发生变化时会自动获取当前剪切板的内容进行索引，并将索引结果以弹窗的方式展示出来，弹窗的样式可在 /src/main/java/util/WindowTip.java 中自由修改，需要有Java Swing的基础知识

6.bug反馈：

提交Issues或fork本仓库进行修改后PR

7.词条插入工具：

GUI版词条插入工具，程序位于/src/main/java/admin/GUI_addItem.java



* 建议

若鼠标支持鼠标宏，可将ctrl+c快捷键绑定到侧键位，提高效率




### 开发进度

Main.java可正常启动运行，必须在命令行或IDE中启动。使用前需部署对应mysql服务器

GUI_addItem.java为GUI化的词条插入工具

暂时停止开发客户端GUI界面

### 更新日志

**---beta v0.3   2020/7/12**

1.更新代码异常处理部分，解决目前为止可能引发程序奔溃的所有部分

2.更新GUI版词条插入工具，程序位于/src/main/java/admin/GUI_addItem.java

3.优化代码逻辑，现在可以在/src/main/java/util/WindowTip.java的代码头部修改弹窗基本属性

4.数据库连接信息统一移至 /src/main/resources/config.properties 中

5.更多小细节改善...



**---beta v0.2  2020/6/27** 

更新CommandLine模式下的程序逻辑，优化代码



**项目组件参考：**

[1.剪切板操作功能 ](https://blog.csdn.net/xietansheng/article/details/70478266 "1.剪切板操作功能 ")

[2.弹窗提醒样式 ](https://www.cnblogs.com/hgxbo/p/5508384.html "2.弹窗提醒样式 ")
