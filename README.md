# MistyRain
### Just a study mod for Minecracft 1.12.2✓✕

### 实现
- 灵气: Anima
	- 灵气等级: AnimaLevel
	- 灵气种类: AnimaType
- 百草书: HerbalBook
	- 主键: key
	- 主目录: mainDirectories,List<Directory>
		- 物品图标: registryItem
		- 主键: key
		- 物品列表: items,List<Items>
			- 物品图标: registryItem
			- 主键: key
			- 文本:
				- 标题: upperskey.upperkey.key.title
				- 页数: upperskey.upperkey.key.pageSize
				- 内容: 按内容数获取，例: upperskey.upperkey.key.content.0
		- 文本:
			- 标题: upperkey.key.title
	- 文本:
		- 上一页: key.btnUpPage
		- 上一级: key.btnUpper
		- 下一页: key.btnNextPage
- 时间:
	- 每次获取访问当前维度的Time并用于计算
	- 二十四节气:
		- 月份表示:因有范围，整数部分表示月份，其中0-11表示1至12月，浮点数部份表示当月的第几天
		- 天数: 总天数
	- 季节: 获取月份所代表的季节


### 机制
- 影响植物的因素
	- 光照: 分天空光(日光/月光)，分冷暖光(冷色光/暖色光)
	- 水份: 依据水的种类
	- 温度: 主要依据花盆的温度判定，花盆可调温
	- 土壤: 依据土壤的种类
	- 灵气: 依据灵气的种类，缺少灵气时不产生灵气(不具特殊效果)
	- 材质: 影响承载的土壤种类和水种类，材质不同具有特殊效果
- 灵气
	- 种类: 灵金，灵木，灵水，灵火，灵土，灵空
	- 组合: 不同组合可产生额外的灵气组合
		- 双灵: 按需设定，且跟比例有关
		- 三灵: 按需设定，且跟比例有关
		- 四灵: 按需设定，且跟比例有关
	- 等级: 由产出灵气的盆栽决定
		- 虚无: 无产出
		- 稀薄: 基本产出等级
		- 充盈: 次级产出等级
		- 浓郁: 最大产出等级
- 花盆
	- 温度: 植物生长所需温度以此为准
	- 水份: 储存水的种类和容量依据花盆材质
	- 土壤: 储存土壤的种类依据花盆材质
	- 灵气: 理论上不存储，只在访问时获得种类和等级的信息
	- 材质
		- 设定: 只记录灵气散发方式和特殊效果
		- 基础: 四周散发基础等级的灵空，无特殊效果
		- 润泽: 若收到灵水则产生水满足自身
- 时间系统
	- 二十四节气: 植物生长状态所需的参照
	- 月份/天数: 只用于计算
	- 春夏秋冬: 若天数较低时，只显示季节而不显示节气，但实际仍依节气判断


### 盆栽
- 雪绒花
	- 用途: 基本的产生灵气的植物
	- 特征: 绒球状，冬春开花，常年绿
	- 消耗: 消耗水，肥料，光照
	- 产生: 灵空(开花阶段)
- 洋甘菊
	- 用途: 基本的产生灵气的植物
	- 特征: 丛簇状，夏秋开花，常年绿
	- 消耗：消耗水，肥料，光照
	- 产生: 灵空(开花阶段)
- 水仙花
	- 用途: 基本的产生灵水的植物
	- 特征: 丛簇状，常年间隔开花，周期性枯萎，七天一周期
	- 消耗: 消耗水，肥料，灵空
	- 产生: 灵水(所有阶段，等级不一样)
- 萝青藤
	- 用途: 基本的产生灵木的植物
	- 特征: 藤蔓状，常年绿
	- 消耗: 消耗水，肥料，灵空
	- 产生: 灵木(所有阶段)
- 烛心草
	- 用途: 基本的产生灵火的植物
	- 特征: 丛簇状，常年绿，极小概率开花
	- 消耗: 消耗水，肥料，灵空
	- 产生: 灵火(所有阶段)
- 落红梅
	- 用途: 基本的产生灵土的植物
	- 特征: 灌木状，常年绿，季节性开花
	- 消耗: 消耗水，肥料，灵空
	- 产生: 灵土(所有阶段)
- 子风藤
	- 用途: 基本的产生灵金的植物
	- 特征: 藤蔓状，常年绿，按季开花
	- 消耗: 消耗水，肥料，灵空
	- 产生: 灵金(开花阶段)

#TODO:
	- 时间系统
	- 指令设定时间相关内容
	- TileEntityTick相关内容