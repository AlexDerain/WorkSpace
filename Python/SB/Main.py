from math import *

# 键盘输入边界条件
print("请输入长方形的四边温度条件    Tleft   Tright  Tdown   Ttop")
Tleft, Tright, Tdown, Ttop = input().split()
Tleft = float(Tleft)
Tright = float(Tright)
Tdown = float(Tdown)
Ttop = float(Ttop)

# 边界条件设置
M = 112
N = 112
T = [[0.0] * N for i in range(M)]
Tcopy = [[0.0] * N for i in range(M)]
for i in range(1, M - 1):
    T[i][0] = Tcopy[i][0] = Tleft
    T[i][19] = Tcopy[i][19] = Tright
for i in range(0, N - 1):
    T[0][i] = Tcopy[0][i] = Ttop
    T[9][i] = Tcopy[9][i] = Tdown

# 初始化
for i in range(1, M - 1):
    for j in range(1, N - 1):
        T[i][j] = Tcopy[i][j] = 0.25 * (Tleft + Tright + Tdown + Ttop)

# 迭代计算
dif = [[0.0] * N for i in range(M)]
mmax = 0.0
temp = 0.0
for i in range(0, M):
    for j in range(0, N):
        dif[i][j] = 1.0
e = 0.001
for k in range(1, 100):
    for i in range(1, M - 1):
        for j in range(1, N - 1):
            T[i][j] = 0.25 * (T[i - 1][j] + T[i + 1][j] + T[i][j + 1] + T[i][j - 1])
    for i in range(1, M - 1):
        for j in range(1, N - 1):
            dif[i][j] = fabs(T[i][j] - Tcopy[i][j])
        mmax = dif[1][1]
    for i in range(1, M - 1):
        for j in range(1, N - 1):
            if mmax < dif[i][j]:
                mmax = dif[i][j]
    if mmax <= e:
        break
    for i in range(1, M - 1):
        for j in range(1, N - 1):
            Tcopy[i][j] = T[i][j]

for j in range(0, N):
    for i in range(0, M):
        print(T[i][j], end='\t')
        if (i + 1) % M == 0:
            print('\n')
