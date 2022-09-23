import socket
import threading
import json
from svm.svm_predict import svm_predict, emotion_predict, turn_head
# 导入处理表情函数
import time
import re
import numpy as np


def main():
    # 创建服务器套接字
    serversocket = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
    # 获取本地主机名称
    host = socket.gethostname()
    # host = '127.0.0.1'
    # host = '0.0.0.0'
    # 设置一个端口
    port = 55533
    # 将套接字与本地主机和端口绑定
    serversocket.bind((host, port))
    # 设置监听最大连接数
    serversocket.listen(5)
    # 获取本地服务器的连接信息
    myaddr = serversocket.getsockname()
    print("服务器地址:%s" % str(myaddr))
    while True:
        # 获取一个客户端连接
        clientsocket, addr = serversocket.accept()
        print("连接地址:%s" % str(addr))
        try:
             t = ServerThreading(clientsocket)  # 为每一个请求开启一个处理线程
             t.start()
             # time.sleep(5)
             t.stop()

        except Exception as identifier:
            print(identifier)
#        try:
#            print(threading.active_count())
#            t = ServerThreading(clientsocket)  # 为每一个请求开启一个处理线程
#            t.start()
#            # time.sleep(5)
#            # t.stop()
#
#        except Exception as identifier:
#            print(identifier)


class ServerThreading(threading.Thread):

    def __init__(self, clientsocket, recvsize=1024 * 1024, encoding="utf-8"):
        threading.Thread.__init__(self)
        self._socket = clientsocket
        self._recvsize = recvsize
        self._encoding = encoding
        self.flag = True

    def run(self):
        print("开启线程.....")
        while True:
            try:
                # 接受数据
                msg = ''
                while True:
                    # 读取recvsize个字节
                    rec = self._socket.recv(self._recvsize)
                    print(len(rec) == 0)
                    if len(rec) == 0:
                        break

                        # 解码
                    msg += rec.decode(self._encoding)
                    # print(msg)
                    # 文本接受是否完毕，因为python socket不能自己判断接收数据是否完毕，
                    # 所以需要自定义协议标志数据接受完毕
                    if msg.strip().endswith('over'):
                        msg = re.match(r'\{[^\}]+\}', msg).group()
                        break
                        # if msg.strip().endswith('end'):
                        #  self._socket.close()
                        #  break
                        # if msg.strip().endswith('end'):
                        #  break
                print(msg)
                    # 将str类型的数据转换为dict类型
                msg_dict = json.loads(msg)
                    # 预测疲劳
                pred = svm_predict(msg_dict['P_70']+0.20, msg_dict['max_mouth'])[0]
                print('{:.2f} {}'.format(msg_dict['P_70'], msg_dict['max_mouth']))
                #                print(pred)

                # 预测心情
                # 拿到a-嘴巴咧开程度  b-嘴巴张开程度  c-眉毛高度占比 dd-眉毛距离占比  e-眼睛睁开程度 将其存入二维数组
                # a = msg_dict['a'], b = msg_dict['b'], c = msg_dict['c'], d = msg_dict['d'], e = msg_dict['e']
                x = [[msg_dict['a'], msg_dict['b'], msg_dict['c'], msg_dict['d'], msg_dict['e']]]
                # print(x)

                # x_happy1=[[0.469, 0.078, 0.08, 0.395, 0.063]]
                # x_happy2 = [[0.466, 0.067, 0.08, 0.401, 0.064]]
                # x_sadness1 = [[0.386, -0.009, 0.096, 0.456, 0.072]]
                # x_sadness2 = [[0.379, -0.006, 0.098, 0.453, 0.07]]
                # x_normal1= [[0.288, 0.016, 0.229, 0.458, 0.042]]
                # x_normal2= [[0.288, 0.013, 0.227, 0.462, 0.044]]
                # x_angry1=[[0.379, 0.009, 0.123, 0.45, 0.082]]
                # x_angry2= [[0.374, -0.04, 0.127, 0.455, 0.082]]
                # x_surprise1=[[0.361,0.132, 0.07, 0.423, 0.078]]
                # x_surprise2=[[0.359, 0.132, 0.074, 0.436, 0.075]]
                emotion_res = emotion_predict(x)
                #                print(emotion_res)

                # 预测转头
                # 表示浮点二维n*2矩阵,整型可用np.array(rightTurn_arr,dtype=float)转化
                # noturn_arr = [[338., 212.], [391., 210.],
                #               [410., 210.], [458., 220.],
                #               [354., 231.], [378., 234.],
                #               [418., 236.], [442., 236.],
                #               [381., 279.], [413., 281.],
                #               [369., 308.], [418., 311.],
                #               [394., 316.], [388., 356.]]
                # leftTurn_arr = [[380, 199], [435, 198],
                #                 [448, 203], [466, 220],
                #                 [393, 216], [416, 221],
                #                 [444, 228], [459, 234],
                #                 [423, 263], [445, 267],
                #                 [402, 288], [438, 296],
                #                 [426, 297], [410, 338]]
                # rightTurn_arr=[[263, 201], [295, 188],
                #                [311, 182], [367, 184],
                #                 [275, 215], [295, 211],
                #                [327, 205], [351, 201],
                #                 [295, 249], [322, 247],
                #                [294, 279], [335, 275],
                #                [309, 283], [318, 322]]
                # print(np.array(noturn_arr))
                head_res = turn_head(np.array(msg_dict['head_points'], dtype=float))
                #                print(head_res)
                # sendmsg = {
                #     'uuid': msg_dict['uuid'],
                #     'pred': pred,
                #     'timestamp': int(time.time() * 1000),
                # }

                sendmsg = {
                        'emotion_res': emotion_res,
                        'head_res': head_res,
                        # 'uuid': msg_dict['uuid'],
                        'pred'  : pred,
                        'timestamp': int(time.time() * 1000),
                }
                print(sendmsg)
                sendmsg = json.dumps(sendmsg)
                self._socket.send(sendmsg.encode(self._encoding) + bytes('\n', encoding=self._encoding))
            except Exception as identifier:
                    self._socket.send("500".encode(self._encoding))
                    print(identifier)
                    pass

            finally:
                    self._socket.close()
                    self.stop()
                    print(threading.active_count())

            print("任务结束.....")
            break

    def stop(self):
        self.flag = False


if __name__ == "__main__":
    main()
