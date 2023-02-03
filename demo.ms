

/*
    这是多行注释，必须以/*开头，*/结尾
*/





// getEnabled <建筑物> <变量名>
// 获取某个建筑的启用状态

getEnabled switch1 data

// setEnabled <建筑物> <数值/变量名>
// 设置某个建筑的启用状态
setEnabled duo1 data

// printMsg <信息板建筑名> <内容/变量名>
// 将信息打印到信息板
printMsg message1 "TEST 666"

// 获取上一行命令在编译后的第几行
getLine test

// 获取上一行命令在编译后的第几行，设置为全局替换规则
getLineG wtf

// 调用规则
setEnabled duo1 @=wtf

// 自带命令兼容，使用自带命令"wait"等待0.5秒
wait 0.5

// 如果 data == 1 就结束本次运行
ifEnd data == 1

// 直接结束本次运行
ifEnd @true

// 直接跳转到 第1行
jumpIf 1 @true

// 如果 data == 0 就跳转到 第2行
jumpIf 2 data == 0

// 计算，将 testing 设置为 6 * 2 的值
calc testing = 6 * 2

// 计算，将 testing 设置为 0 ~ 1 的随机数
calc testing = 6 rand

// 将内存 ram1 中的第 33 格的值读取到 data2 变量
ramRead data2 = ram1 32

// 将变量 data 的值写到内存 ram1 中的第 32 格
ramWrite ram1 31 = data

























