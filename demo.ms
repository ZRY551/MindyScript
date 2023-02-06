

/*
    这是多行注释，必须以/*开头，*/结尾
*/



// 将 demo.png 图像的内容以像素画的形式打印到屏幕 display1 上，显示屏长宽均为 176 像素
// 使用此命令时，图像大小必须和设定的像素长宽一致
// 如果图像长宽比显示屏实际可显示长宽大，那么只会显示一部分
// 如果图像长宽比显示屏实际可显示长宽小，那么图像将在左下角显示
// 此命令不支持多屏幕协同显示和多处理器协同！
// 注意：可能因为逻辑代码上限无法被正常显示！可能需要多个逻辑处理器协同！如果遇到这种情况请直接使用像素图片解析器
// 直接使用像素图片解析器获得多个逻辑处理器/显示屏的逻辑代码！（推荐）
// Small: 80*80 , Big: 176*176
// pixel <图像长> <图像宽> <显示屏建筑名称> <图像路径>...
// pixel 176 176 display1 demo.png
// pixel 32 32 display2 demo.png



end

// getEnabled <建筑物> <变量名>
// 获取某个建筑的启用状态

getEnabled switch1 data

// setEnabled <建筑物> <数值/变量名>
// 设置某个建筑的启用状态
setEnabled duo1 data

// printMsg <信息板建筑名> <内容/变量名>...
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


// 定义一个函数
func test (a,b) {
    // 函数中使用命令
    getEnabled switch6 data6
    getLine d66
    setEnabled duo13 d66

}

func test2 (a,b) {
    // @P=<参数名> 来使用函数提供的参数
    getEnabled switch9 @P=a
    // 函数中使用命令
    getEnabled switch8 data8
    // 获取上一句命令编译后的位置并传入全局
    getLineG FkPT
    // 从全局中获取信息
    setEnabled switch5 @F=FkPT

}

// 函数外使用全局位置参数
setEnabled switch5 @F=FkPT




// 实例：参数传递（无返回值函数）
func test3(isOpen_bt1,isOpen_door1){

    setEnabled switch1 bt1
    setEnabled door1 isOpen_door1

}





runFunc test3 data1 data2

end













