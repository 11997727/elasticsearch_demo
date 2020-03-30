/*
Navicat MySQL Data Transfer

Source Server         : 阿里云docker
Source Server Version : 50729
Source Host           : 123.57.128.164:3307
Source Database       : news

Target Server Type    : MYSQL
Target Server Version : 50729
File Encoding         : 65001

Date: 2020-03-30 20:48:02
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for news_category
-- ----------------------------
DROP TABLE IF EXISTS `news_category`;
CREATE TABLE `news_category` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '新闻分类编号',
  `name` varchar(255) NOT NULL COMMENT '新闻分类名称',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of news_category
-- ----------------------------
INSERT INTO `news_category` VALUES ('1', '军事');
INSERT INTO `news_category` VALUES ('2', '财经');
INSERT INTO `news_category` VALUES ('3', '娱乐');
INSERT INTO `news_category` VALUES ('4', '体育');

-- ----------------------------
-- Table structure for news_detail
-- ----------------------------
DROP TABLE IF EXISTS `news_detail`;
CREATE TABLE `news_detail` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '新闻编号',
  `categoryId` int(11) NOT NULL COMMENT '新闻分类编号',
  `title` varchar(255) NOT NULL COMMENT '新闻标题',
  `summary` varchar(255) DEFAULT NULL COMMENT '新闻摘要',
  `author` varchar(255) DEFAULT NULL COMMENT '作者',
  `createDate` datetime NOT NULL COMMENT '创建时间',
  `updateDate` datetime NOT NULL COMMENT '更新时间',
  `price` double(10,2) DEFAULT NULL COMMENT '打赏',
  `image` varchar(255) DEFAULT NULL COMMENT '图片',
  PRIMARY KEY (`id`),
  KEY `detail_id` (`categoryId`) USING BTREE,
  CONSTRAINT `news_detail_ibfk_1` FOREIGN KEY (`categoryId`) REFERENCES `news_category` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of news_detail
-- ----------------------------
INSERT INTO `news_detail` VALUES ('1', '1', '\"71架伊尔-76空降2000伞兵！俄军演场面堪比战争电影\"', '\"今天（9月21日），在中央档案馆公布12分钟开国大典彩色视频的同时，@人民日报 微博发了一段1949年开国阅兵与2015年“九三”阅兵的对比视频\"', '张三', '2019-09-01 22:52:05', '2019-09-23 22:52:15', '23.50', 'asdasdasda');
INSERT INTO `news_detail` VALUES ('2', '1', '\"亲历者讲述：67天的战斗，我们打赢了“埃博拉阻击战”\"', '\"在67天的战斗中，中国赴利比里亚抗埃医疗队累计接诊患者112例，队员无一人感染，实现了“打胜仗、零感染”的目标\"', '张三', '2019-09-02 18:53:12', '2019-09-23 22:53:21', '55.20', 'asdasdsd');
INSERT INTO `news_detail` VALUES ('3', '1', '\"向军旗敬最后一个军礼！湖北省军区举行军官退役仪式\"', '\"楚天都市报9月23日讯（记者陈倩 通讯员徐国山 朱勇 粟毅 徐晨）9月23日，湖北省军区机关及直属单位隆重举行2019年军官（文职干部）退役仪式，为退役老战友送别。\"', '张三炮', '2019-09-05 20:55:21', '2019-09-23 22:55:35', '66.00', 'asdasd');
INSERT INTO `news_detail` VALUES ('4', '2', '\"尤文上赛季财务报表：收入6.2亿欧，亏损3990万\"', '\"虎扑9月21日讯 尤文图斯俱乐部公布了上赛季的年度财务报表，从中已经可以感受得到C罗带来的影响：他们上赛季的营业额为6.215亿欧元，而损失也达到了3990万欧。\"', '李四', '2019-09-06 18:56:37', '2019-09-23 22:56:44', '72.50', 'asdasd');
INSERT INTO `news_detail` VALUES ('5', '2', '\"超级寡头时代，买房要买“三个大”！\"', '\"无论是从单个房企还是前20强房企整体来看，过去十年，房地产企业的巨头化、头部化的趋势异常明显。互联网行业那种高度集中，一家或者几家独大的现象，将是房地产行业下一步发展的必然趋势。前一百年，世界属于欧美，后一百年无疑将属于中国。这是历程的进程，也是时代的必然！\"', '李四', '2019-09-07 19:57:21', '2019-09-23 22:57:31', '81.90', 'asdasd');
INSERT INTO `news_detail` VALUES ('6', '2', '\"特朗普暂时排除军事选项避险情绪降温 黄金持稳多空谨慎布局\"', '\"24K99(北美)讯现货黄金周五(9月20日)窄幅震荡，美市盘中最高触及1506.80美元/盎司，暂时重返1500美元关口上方，由于美国总统特朗普暂时排除了军事行动选项，这令避险情绪有所降温，因此金价反弹空间较为受限。与此同时，欧盟委员会主席容克周四告诉英国天空新闻(Sky News)，“我们可以就英国脱欧达成协议”，进一步削弱了无协议脱欧风险。\"', '李四', '2019-09-08 17:58:11', '2019-09-23 22:58:31', '33.50', 'asdasd');
INSERT INTO `news_detail` VALUES ('7', '3', '\"马伊琍离婚后难走出情伤性情大变？不理旁人问候孤独回家背影落寞\"', '\"前不久马伊琍与文章离婚的消息造成了轰动，这份因为文章出轨而产生裂痕的婚姻终归还是没能挽回。十一年的感情告一段落让许多网友心生感慨，当事双方也各自开启了新生活。\"	', '王五', '2019-09-09 15:59:17', '2019-09-23 22:59:25', '44.50', 'asdasd');
INSERT INTO `news_detail` VALUES ('8', '4', '\"中国女排登上《新闻联播》：七连胜为蝉联冠军打下坚实基础\"', '\"北京时间9月23日消息，中国女排3-0横扫美国女排，朱婷砍下全场最高23分，她在赛后也登上了热搜。本届女排世界杯在举行，现场有不少日本记者和球迷，看到朱婷的统治级表现，日本记者也被征服了。此外，《新闻联播》也关注到了中国女排和朱婷！\"', '钱六', '2019-09-11 18:00:08', '2019-09-23 23:00:17', '100.50', 'asdasdas');
