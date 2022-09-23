import joblib
import pickle
import numpy as np
import cv2


# from sklearn.externals import joblib 使用docker加载报错
def returnNum(str):
    num=0
    if str=='happy':
        num=0
    elif str=='sadness':
        num=1
    elif str=='angry':
        num=2
    elif str=='normal':
        num=3
    elif str=='surprise':
        num=4
    return num

def svm_predict(eyes, mouth):
    with open('svm/svm_model_7.pickle', 'rb') as f:
        model = pickle.load(f)
        pred = model.predict(np.array([[eyes, mouth]],dtype=float))
        return pred


def emotion_predict(x):
    cld = joblib.load("svm/NEmoknn.m")  # 调用已经训练好的模型
    kll = int(cld.predict(x))  # 用训练好的KNN模型对获取的特征值进行分析分类，返回分类后的标签
    # 根据标签判断当前表情
    emo = ['angry', 'sadness', 'nature', 'nature', 'happy', 'normal', 'surprise']
    # print(emo[kll - 1])
    return returnNum(emo[kll-1])


def turn_head(shape):
    K = [6.5308391993466671e+002, 0.0, 3.1950000000000000e+002,
         0.0, 6.5308391993466671e+002, 2.3950000000000000e+002,
         0.0, 0.0, 1.0]
    D = [7.0834633684407095e-002, 6.9140193737175351e-002, 0.0, 0.0, -1.3073460323689292e+000]
    # 模拟相机内参
    cam_matrix = np.array(K).reshape(3, 3).astype(np.float32)
    # print(cam_matrix)
    #  相机畸变系数
    dist_coeffs = np.array(D).reshape(5, 1).astype(np.float32)
    # print(dist_coeffs)
    # 3D通用刚体模型中的14个点的坐标:对应Dlib上点的序号为17, 21, 22, 26, 36, 39, 42, 45, 31, 35, 48, 54, 57, 8
    object_pts = np.float32([[6.825897, 6.760612, 4.402142],
                             [1.330353, 7.122144, 6.903745],
                             [-1.330353, 7.122144, 6.903745],
                             [-6.825897, 6.760612, 4.402142],
                             [5.311432, 5.485328, 3.987654],
                             [1.789930, 5.393625, 4.413414],
                             [-1.789930, 5.393625, 4.413414],
                             [-5.311432, 5.485328, 3.987654],
                             [2.005628, 1.409845, 6.165652],
                             [-2.005628, 1.409845, 6.165652],
                             [2.774015, -2.080775, 5.048531],
                             [-2.774015, -2.080775, 5.048531],
                             [0.000000, -3.116408, 6.097667],
                             [0.000000, -7.415691, 4.070434]])

    reprojectsrc = np.float32([[10.0, 10.0, 10.0],
                               [10.0, 10.0, -10.0],
                               [10.0, -10.0, -10.0],
                               [10.0, -10.0, 10.0],
                               [-10.0, 10.0, 10.0],
                               [-10.0, 10.0, -10.0],
                               [-10.0, -10.0, -10.0],
                               [-10.0, -10.0, 10.0]])
    # solvePnP计算姿势——求解旋转和平移矩阵： 相机到世界坐标系
    # rotation_vec表示旋转矩阵，translation_vec表示平移矩阵，cam_matrix与K矩阵对应，dist_coeffs与D矩阵对应
    _, rotation_vec, translation_vec = cv2.solvePnP(object_pts, shape, cam_matrix, dist_coeffs)
    # projectPoints重新投影误差：原2d点和重投影2d点的距离（输入3d点、相机内参、相机畸变、r、t，输出重投影2d点）
    reprojectdst, _ = cv2.projectPoints(reprojectsrc, rotation_vec, translation_vec, cam_matrix,
                                        dist_coeffs)

    reprojectdst = tuple(map(tuple, reprojectdst.reshape(8, 2)))  # 以8行2列显示

    # calc euler angle 计算欧拉角
    rotation_mat, _ = cv2.Rodrigues(rotation_vec)  # 罗德里格斯公式（将旋转矩阵转换为旋转向量）
    pose_mat = cv2.hconcat((rotation_mat, translation_vec))  # 水平拼接
    # decomposeProjectionMatrix将投影矩阵分解为旋转矩阵和相机矩阵
    _, _, _, _, _, _, euler_angle = cv2.decomposeProjectionMatrix(pose_mat)
    # Y表示左右摇头  左负右正 euler_angle[1][0]
    # 左右有转头返回1
    if (euler_angle[1, 0] < -20 or euler_angle[1, 0] > 20):
        return 1
    else:  # 否则返回0
        return 0
