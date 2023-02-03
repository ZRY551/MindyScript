







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

calc testing = 6 * 2

calc testing = 6 rand


























