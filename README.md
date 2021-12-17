# MistyRain
##### Just a study mod for Minecracft 1.12.2

### 一、机制
#####(1)盆栽
- 生长状态：
    - 花籽/根茎，幼芽，抽叶，成株，花蕾，花开，结果，果熟，枯萎
- 影响因素：
    - 时间段，光照，肥料，土壤，水分，温度
    - 杂草，害虫，松土
- 花盆因素：
    - 材质决定水分流失率，土壤类型，植物适应性
    - 大小决定土壤量，决定生长值的上限
- 花盆
    - 木雕盆
      - 中型，流失中，土壤普通，适合普通的植物

#####(2)盆栽植物
- 白绒球


#####(3)洒水壶
- 机制：
  - 木制，瓦制，魔法，只影响蓄水上限
  - 浇水范围为鼠标所指的花盆的3X3X1的范围，每消耗10%水为每个花盆补充该值的水分
  - 右键浇水，Shift右键放置，浇水会产生特效，根据水的不同，粒子效果不同

### 二、学习笔记
##### (1)碰到的问题及解决方法
- 如何右键持续激活物品
  - 参照ItemFood,覆写getItemUseAction，设置getMaxItemUseDuration并在onItemRightClick中持续激活
- 如何避免右键持续激活物品时对着某些方块失效
  - 在onBlockActived中针对该物品返回False
- 如何为物品添加储水功能且能右键取水
  - 获取指定视线的方块参考ItemGlassBottle,右键取水采用onItemRightClick
  - 储水功能，参考FluidHandlerItemStack
- 添加物品信息和Shift查看的信息
  - 参考addInformation，若Shift，则参考ITooltipFlag