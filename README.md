# Android

Android作为蓝牙 web 数据库服务器 算法服务器四个数据源数据交互的媒介，采用响应式编程的思想，用数据驱动UI，极大的简化了数据处理的逻辑。通过蓝牙接收超声波测距元件和心率传感器光感头部超声、心率红外数据后，对数据归一处理，进行功能数据渲染，对获取到的数据进行初步处理，推送至算法服务器和后端服务器。



# Algorithm

## env

```python
cuda ==11.6
cudnn == 8.3.0

joblib                 1.1.0
numpy                  1.21.5
opencv-python-headless 4.6.0.66
pip                    22.1.2
scikit-learn           0.23.2
scipy                  1.1.0
setuptools             57.5.0
threadpoolctl          3.1.0
wheel                  0.37.1
```

## fuction

+ 训练好的模型文件
  + NEmoknn.m--KNN+CK+数据集,心情预测
  + svm_model_7.pickle--SVM+Yawdd数据集,疲劳预测
  + turn_head函数转头判断---头部姿态估计算法

## run

```cmd
docker build -t hjws .
docker images # 查看hjws对应的镜像
docker run -d -it -p55533:55533 ${镜像id}
```

# Server

使用Java和springboot框架为主进行开发，Java适合开发高并发、I/O 密集的复杂业务场景，springboot 在开发时可减少配置文件添加，可减少代码冗余和便于后期维护升级。数据库同时使用mysql和redis，使用mysql管理用户数据，可以和springboot 的持久化操作接口完美结合，提高程序的稳定性和健壮性。redis 性能高速度快，具有丰富的数据类型，有利于提高程序读写速度。

数据库既存储驾驶员异常驾驶行为信息，包括驾驶过程是否疲劳、心情是否消极、是否有酒驾、长时间不目视前方等，还存储用户个人信息、驾驶员好友、交友圈文章、等其他信息。 

# Web

web基于Vue使用Element UI及vant UI渲染页面并通过Nginx部署上线，最终与安卓通过webview实现混合开发客户端。



## function

+ 更多功能页
  + 车友圈
  + 消息聊天
  + 驾驶记录
  + 好友驾驶
  + 车载音乐
  + 车载商城
+ 个人中心页

## run

### Project setup

```
npm install
```

### Compiles and hot-reloads for development

```
npm run serve
```

### Compiles and minifies for production

```
npm run build
```

### Customize configuration

See [Configuration Reference](https://cli.vuejs.org/config/).
