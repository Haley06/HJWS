# 基于镜像基础
FROM python:3.7
# 设置代码文件夹工作目录 /app
WORKDIR /app
# 复制当前代码文件到容器中 /app
ADD . /app
# 安装所需要的包
RUN pip install --upgrade pip
RUN pip install joblib
RUN pip install numpy==1.21.5
RUN pip install scikit-learn==0.19.2
RUN pip install opencv-python-headless
# Run app.py when the container launches
CMD ["python", "web_pred.py"]