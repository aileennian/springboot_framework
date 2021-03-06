package com.nian.utils.zip;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

public class CompressUtil {
//	public static void main(String[] args) {
//		try {
//			String str = "黑天帝的手掌深沉幽暗，仿佛宝石雕琢，能够吸纳所有光线，挣脱了时光的束缚，探向着几个刹那前的过去，要抓住彼时的孟奇"+
//"　　借助彼岸特征、诸果之因和八九玄功对危险的预知，孟奇“看”到了时光长河的异常，感觉到了即将变化的历史，然而面对这一切，他是那样的无能为力，根本没有办法去阻止，就像开窍时遭遇了法身一样，差距甚至远胜于此，似乎只能眼睁睁目睹黑天帝之手伸向以往的自己。"+
//""+
//"　　这是层次的分别，本质的高低，只有彼岸者才能对抗彼岸者，其余人等，数量再多，再是集聚，也没有任何作用！"+
//""+
//"　　时光长河水波一阵涟漪，美得如梦似幻，那只黑色宝石般的手掌即将触碰到几个刹那前的孟奇。"+
//""+
//"　　就在这时，孟奇所化混沌奇点忽地膨胀，像是要展开成庆云，继而刀光剑气浮现，分别斩向了时光长河的上游与下游。"+
//""+
//"　　刀光幽暗，似从虚幻因果里斩出，剑气纵横，仿佛由时光与命运之河孕育。"+
//""+
//"　　过去种种，烟消云散，此刀不问前尘！"+
//""+
//"　　未来命运，只取一条，此剑不求来世！"+
//""+
//"　　哗啦！"+
//""+
//"　　时光长河涟漪加剧，竟被孟奇全力爆发的不问前尘之刀和不求来世之剑略微影响到了，然后只见刀光剑气急速收缩，与太上无极元始庆云共同坍缩，带动周围一切复归最初。"+
//""+
//"　　哗啦！"+
//""+
//"　　无极混沌之意拉动了虚幻的时光长河，荡起明显波浪，虚空弯折，宙光迟缓，纷纷汇入了那一点，没有上下左右，也将丢失过去现在和未来定义的深邃之点！"+
//""+
//"　　当无极印修炼到造化圆满境界时，包容了时光大道的它便能像主修时光类功法的顶峰级大神通者般影响某个范围内的虚幻长河，有限度撑过历史的改变，窥视未来，一如七杀碑，只不过双方原理截然不同，前者是真正回溯时光，修改过去，而无极印是将一定程度内的历史和未来拉动蜷缩至当前，让自身“横三世一体”，对抗外力的影响，以防御为主。"+
//""+
//"　　倒是开天印和道一印配合下，能斩破时光束缚与乱流，若修持至造化圆满，将有七杀碑的神异，以当前为因，使过去为果，颠倒逻辑！"+
//""+
//"　　这便是“元始金章”首三印的强横之处，在彼岸前的时光之道这个意义上而言，甚至胜过部分如来神掌与截天七剑。"+
//""+
//"　　当此危急时刻，孟奇没有将希望完全寄托于青帝的出手，而是竭尽了自身所能，先以自创的两大杀招“不问前尘”“不求来世”撬动了过去与未来，给全力爆发的无极印创造了蜷缩十个刹那内时光长河的机会！"+
//""+
//"　　原本孟奇的无极印是远远达不到影响时光这个程度的，即使之前炼化部分生死原点有成，混沌之意已然凝聚出虚幻大道，等同初入造化的水准，也还不够，但此时他身处九幽，变化特征，瞒天过海，境界得到了本质的提升，从传说步入了造化，无极印亦因此有了造化中游的层次。"+
//""+
//"　　当然，这距离造化圆满依旧遥远，可恰好孟奇法身前的积累、领悟和突破方向涉及了过去因果和未来命运，自创了一刀一剑神技，本身“到”了造化境界后，它们亦是有了本质的提升，对命运的干涉加强，对过去的联系从前世略微深入到以往，故而能也仅能影响时光长河少许，让它荡起涟漪，为无极印创造机会。"+
//""+
//"　　哗啦！"+
//""+
//"　　时光长河仿佛有所弯折，向着孟奇所化那混沌奇点扭曲，过去五个刹那与未来五个刹那内的一道道自家身影以远近顺序纷纷向着那一点汇聚而去，要浑然一体，再无法被改变！"+
//""+
//"　　哗啦！"+
//""+
//"　　即将被黑天帝之手握住的那个“过去孟奇”被突然激烈的河水缓缓“冲”向当前节点，让那深沉幽暗的手掌未能直接抓到！"+
//""+
//"　　时光长河波浪之声入耳，孟奇心海震荡，只觉道力瞬间消耗一空，法身有彻底化成混沌之点，再无法恢复的迹象，而虚幻的河水冲刷，他的灵智渐有模糊，像是要被洗去所有记忆，这便是越阶影响时光，对抗历史改变的代价！"+
//""+
//"　　以他目前的实力根本撑不过这种程度的消耗和反噬三个刹那！"+
//""+
//"　　黑天帝微微一怔，手掌转向，依旧准确抓向那个“过去孟奇”，这种程度的时光对抗，祂还不放在眼里！"+
//""+
//"　　“就是这个机会！”"+
//""+
//"　　孟奇心头忽有低吼一声，猛地放开了对时光长河的蜷缩，让过去的自己和未来的自己全都归位，让无极印再次展开成元始庆云，而庆云之上，盘古幡虚影被混沌簇拥，开辟之意凝聚成形——此乃九幽带来的提升。"+
//""+
//"　　哗啦！"+
//""+
//"　　时光长河恢复了正常，改变方向的黑天帝手掌再次错过了“过去孟奇”，然后他眼前一道紫光亮起，像是斩开了虚空，斩开了九幽，斩开了时光，斩向着自己的意识！"+
//""+
//"　　孟奇将自身与霸王绝刀的力量发挥到了极限，开天辟地，惊世一击！"+
//""+
//"　　“哼，蝼蚁的挣扎。”黑天帝屈指一弹，岁月流逝，刀光渐消，竟没有半点波澜。"+
//""+
//"　　或许在这种层次的强者眼中，孟奇刚才那一击真的只相当于蝼蚁的挣扎！"+
//""+
//"　　刀光消散，黑天帝那双淡漠无情的眼眸忽地露出了一丝诧异，因为眼前的孟奇不见了，彻底不见了！"+
//""+
//"　　当然，以祂的境界，毫无疑问感觉到了因果的波动，只是那终点处飘渺难测，竟然无法窥见！"+
//""+
//"　　而此时孟奇已然出现于了生死原点内！"+
//""+
//"　　无论是催发无极印，越阶硬抗时光长河的反噬，还是开天辟地的惊世一击，都是为了隐藏真正的保命手段，免得被黑天帝察觉。"+
//""+
//"　　而这保命手段就是借助诸天生死轮，运转诸果之因，直接降临于生死原点内，以此躲开伪彼岸们的搜索。"+
//""+
//"　　此乃近道之所，时空混一，能挡住彼岸的窥查，当今世上，知道它如何进入的不超过十指之数，黑天帝、玄冥鬼帝等显然不在此列——生死原点能搅乱十息内的过去与未来，让时光长河内的对应身影变得模糊和难以把握，若没有这种能力，早就被彼岸大人物们翻看时光长河内的历史，从他人进入的前后场景判断出具体所在。"+
//""+
//"　　黑天帝目光幽深看着孟奇消失的地方，右手探入虚幻长河内，试图触及对方过去的身影，但那都仿佛镜花水月，一碰就消失，一离开又重新出现。"+
//""+
//"　　再往前，涉及赤色山脉与九幽所眷的魔皇爪，一切又模糊难以窥见。"+
//""+
//"　　至于更早之前，孟奇身在九幽外，过去岂是祂能够影响得了。"+
//""+
//"　　这便是九幽伪彼岸的局限，换做真正彼岸，“生死原点”内的孟奇已经当场暴毙了！"+
//""+
//"　　这番变化兔起鹘落，等到九乱天尊、玄冥鬼帝等从孟奇两大化身自爆中归来，意识扫过，事情已然结束。"+
//""+
//"　　“那是玉虚宫元皇苏孟，他带走了封神榜。”黑天帝意识冷冷传音，“哼，他必定躲在某处，还没有离开九幽，藏身所在很可能便是生死原点，我们意识覆盖所有地方，耐心等待他出来。”"+
//""+
//"　　黄泉两次异动，又认出了孟奇的身份，很容易就将这两件事情与生死原点联系在一起。"+
//""+
//"　　可是，他竟然察觉了孟奇身怀封神榜！"+
//""+
//"　　听见“生死原点”四个字，玄冥鬼帝目光闪烁，冰冷笑道："+
//""+
//"　　“好！”"+
//""+
//"　　…………"+
//""+
//"　　生死原点之内，孟奇忙不迭跳入了蓬勃生机之中，并运转阴阳印，藉此修复自身，刚才时光长河的反噬让他法身险些崩溃，受创极重。"+
//""+
//"　　回想刚才的惊险刺激历程，孟奇身心一阵舒畅。"+
//""+
//"　　自己竟然能于伪彼岸手下暂时逃脱，简直太让人感觉自豪了！"+
//""+
//"　　“哈哈！”之前的压抑沉闷情绪随着孟奇的大笑之声消解了许多，不再是强行摒除。"+
//""+
//"　　自己果然还是喜欢挑战强者，但这种经历多了，必然身首异处，可一不可二。"+
//""+
//"　　孟奇身躯渐渐恢复，笑声忽地止住，嘴角抽搐，自嘲了一句："+
//""+
//"　　“莫要学曹孟德，免得笑出新的敌人。”"+
//""+
//"　　话音未落，他猛然看到了一双脚，一双立于生死原点深处的脚，一双穿着龙鳞所炼制靴子的脚！(未完待续。)";
//			str+=str.trim();
//			str+=str.replaceAll("的", "额");
//			str+=str.toUpperCase();
//			System.out.println(str.length());
//			String comp = compress(str);
//			System.out.println(comp.length());
//			System.out.println(comp);
//			String umComp = uncompress(comp);
//			System.out.println(umComp);
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
//	}

	// 压缩
	public static String compress(String str) throws IOException {
		if (str == null || str.length() == 0) {
			return str;
		}
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		GZIPOutputStream gzip = new GZIPOutputStream(out);
		gzip.write(str.getBytes());
		gzip.close();
		return out.toString("ISO-8859-1");
	}

	// 解压缩
	public static String uncompress(String str) throws IOException {
		if (str == null || str.length() == 0) {
			return str;
		}
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		ByteArrayInputStream in = new ByteArrayInputStream(str.getBytes("ISO-8859-1"));
		GZIPInputStream gunzip = new GZIPInputStream(in);
		byte[] buffer = new byte[256];
		int n;
		while ((n = gunzip.read(buffer)) >= 0) {
			out.write(buffer, 0, n);
		}
		// toString()使用平台默认编码，也可以显式的指定如toString("GBK")
		return out.toString("utf-8");
	}
}
