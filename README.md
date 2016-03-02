##Android GC机制实践调研

================
众所周知，java GC 是影响Android应用性能的主要因素之一。完全交给系统管理的GC往往不尽如人意，而开发者却也毫无办法，只能对着GC迎合啊迎合，想着办法把GC哄开心了呗~

网上也不乏众多的android 内存优化文章，成为开发者的编码守则。但不管怎么遵守，内存管理依然像一个黑盒子一样，反正我是写着不踏实。就比如下面这几种情况：

- System.gc(),真的是随叫随到？
- 软引用弱引用的错误使用
- 你觉得内存释放了，它就真的释放了么?


幸得Android Monitor 提供了内存监视器，起码打开了一个窗口可以让我们看看当前应用的内存到底是什么样的。 那么现在我们就来通过一个小Demo，看看android 的GC到底是怎么样吧。

***测试过程中，很悲痛的验证了不同设备不同系统的GC机制是不一样的，我把它粗糙的区分为灵敏型和不灵敏性，所以，我们开发中还是小心小心再小心吧……例如：某些机型System.gc()会被立刻触发，有些机型毫无响应。***

##概要

本次测试从以下几个方面对Android GC 进行调研

- 主动调用System.gc()，不同状态的GC时机
- 空白Activity所占内存大小及GC时机
- 大内存量的Activity的GC时机
- 奔溃临界值下，对象置NULL，是否还会引起内存溢出
- 内存抖动
- 软引用、弱引用的使用

**本次测试采用两款手机：红米3 .低端机型，手机内存值较低，测试环境更加严苛**


###一.主动调用GC下的GC时机

<img src="./1-初始.png" width = "250" height = "150" alt="红米-初始内存值" align=center />

（红米初始内存值）


#####测试1：创建临时变量，通过主动调用System.gc()观察

<img src="./1-1.png" width = "250" height = "200" alt="创建临时变量" align=center />

可以看到内存立刻增加了十几MB，这里我创建了一个Bitmap加载了一张比较大的图片117.16KB，**内存增加量是远大于图片大小的**。

		//并没有进行显示，仅是创建一个图片
        Bitmap bmp = BitmapFactory.decodeResource(getResources(), R.drawable.big_254);

2分钟后内存一直维持在这个水平，**临时变量的GC时机是完全不能保证的，我们可以理解为，GC线程还没有转到这个地方**

<img src="./1-2.png" width = "250" height = "200" alt="主动调用GC" align=center />

**之后我们多次调用System.gc()，内存监视窗口是没有任何反应的。**


#####测试2：初始化类成员变量，置NULL后，主动调用System.gc()观察

<img src="./1-2.png" width = "250" height = "200" alt="创建类成员变量" align=center />


        this.bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.big_254);
        
        //置NULL
        this.bitmap = null;
        
        //GC
        System.gc();
        


<img src="./1-4.png" width = "250" height = "200" alt="置NULL后销毁" align=center />


**GC依然没有被触发**

**发现更有意思的问题，每点击一次按钮，内存消耗增加0.02MB,世界上果然没有免费的午餐，点击事件又有新的对象产生了**

#####测试3：不停的初始化类成员变量

也就是说不停的调用下面代码

	this.bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.big_254);




<img src="./1-5.png" width = "500" height = "200" alt="多次初始化类成员" align=center />


可以看到一串有趣的现象，似乎只有在内存值在41.78以上时，GC才会被触发。

**内存占用只有达到一定限度时，GC才会开始执行。**

期间也创建过临时变量，GC也多次调用后全无反应。


###二、空Activity占用的内存大小及销毁时间


<img src="./2-1.png" width = "150" height = "150" alt="初始" align=center />

初始值

<img src="./2-2.png" width = "150" height = "150" alt="初次打开" align=center />

初次打开

<img src="./2-3.png" width = "150" height = "150" alt="返回后再次打开" align=center />

返回后再次打开

<img src="./2-4.png" width = "150" height = "150" alt="返回后再次打开" align=center />

返回后再次打开


初始（9.00MB）~ 初次打开（9.40MB）~ 返回后再次打开（9.55MB）~ 返回后再次打开（9.69MB）

**一个空白Activity的内存占用量是0.4MB,返回后再次进入每次增加0.15MB左右，返回键每次增加0~0.01MB左右**

迷之增长……


###三、非空Activity的销毁


通过下面代码增加Activity内存占用量

        Resources res = getResources();
        for (int i = 0; i < 10; i++) {
            Bitmap bmp = BitmapFactory.decodeResource(res, R.drawable.test_100);
            this.bitmapList.add(bmp);
        }
        
 <img src="./3-2.png" width = "150" height = "150" alt="返回后再次打开" align=center />
 
 之后执行
 
 		System.gc();
        finish();
        
  无任何反应。
  
  **无论Activity是否占用大量内存，其销毁的时间都是迟钝的。**
  
  
  
###四、濒临临界值情况下，对象=NULL，再次消耗内存是否会溢出

经测算，当前测试机在190MB+时，将会内存溢出崩溃。


 <img src="./4-1.png" width = "250" height = "150" alt="返回后再次打开" align=center />



对象置NULL

	this.bitmapList = null;


再次增加并显示

	this.img.setImageResource(R.drawable.big_254);


<img src="./4-2.png" width = "250" height = "150" alt="返回后再次打开" align=center />

欣喜的事情发生了，**“类成员置NULL，对防止内存溢出崩溃是有必要的”**



###五、循环内创建（big or small）对象是否会引起内存抖动


<img src="./5-2.png" width = "250" height = "150" alt="返回后再次打开" align=center />

	for (int i = 0; i < 20; i++) {
            Bitmap bmp = BitmapFactory.decodeResource(getResources(), R.drawable.test_100);
        }

创建“小（1MB左右）”对象 

<img src="./5-1.png" width = "250" height = "150" alt="返回后再次打开" align=center />

	for (int i = 0; i < 20; i++) {
            Bitmap bmp = BitmapFactory.decodeResource(getResources(), R.drawable.big_254);
        }

循环内创建“大（10MB左右）”对象会引起严重的内存抖动


**无论对象大小，都应避免在循环内创建对象**


###六、软引用弱引用的错误使用

这个错误似乎很少会有人犯，但感觉还是列出来比较好


	//错误使用
	private SoftReference<List<Bitmap>> softReference = new SoftReference<List<Bitmap>>(new ArrayList<Bitmap>());
    private WeakReference<List<Bitmap>> weakReference = new WeakReference<List<Bitmap>>(new ArrayList<Bitmap>());
    
    
    //正确使用
    private List<SoftReference<Bitmap>> listRefrence = new ArrayList<>();


用上述错误使用方式代码的两种情况

	List<Bitmap> list = softReference.get();
        if (list == null) {
            list = new ArrayList<>();
            softReference = new SoftReference<>(list);
        }
        Resources res = getResources();
        for (int i = 0; i < 10; i++) {
            Bitmap bmp = BitmapFactory.decodeResource(res, R.drawable.big_254);
            list.add(bmp);
        }

	List<Bitmap> list = null;
        Resources res = getResources();
        for (int i = 0; i < 10; i++) {
            Bitmap bmp = BitmapFactory.decodeResource(res, R.drawable.big_254);
            list = weakReference.get();
            if (list == null) {
                list = new ArrayList<>();
                weakReference = new WeakReference<>(list);
            }
            list.add(bmp);
        }
        
      

<img src="./6-3.png" width = "250" height = "150" alt="返回后再次打开" align=center />
 	
内存会一直暴涨到奔溃。软引用弱引用并不会被收回

	//正确使用
	Resources res = getResources();
        for (int i = 0; i < 10; i++) {
            Bitmap bmp = BitmapFactory.decodeResource(res, R.drawable.big_254);
            right.add(new SoftReference<>(bmp));
        }


<img src="./6-2.png" width = "250" height = "150" alt="返回后再次打开" align=center />

**SoftReference 可以看到明显的内存抖动，但是内存不会暴涨。**

<img src="./6-1.png" width = "250" height = "150" alt="返回后再次打开" align=center />

**WeakReference 和 SoftReference 不同的是，牙更深一点，销毁平率更大，验证了弱引用比软引用更容易被销毁~**


##结论



- 对于加载图片来说，内存增加量是远大于图片大小的。
- 临时变量的GC时机是完全不能保证的，我们可以理解为，GC线程还没有转到这个地方。
- System.gc()并不是立刻执行GC的。
- 每点击一次按钮，内存消耗增加0.02MB,世界上没有免费的午餐，点击事件内部是会有新的对象产生的
- 有时，内存占用只有达到一定限度时，GC才会开始被触发。
- 一个空白Activity的内存占用量是0.4MB,返回后再次进入每次增加0.15MB左右，返回键每次增加0~0.01MB左右
- 无论Activity是否占用大量内存，其销毁的时间都是迟钝的。
- 类成员置NULL，对防止内存溢出崩溃是有必要的
- 无论对象大小，都应避免在循环内创建对象
- 注意软引用与弱引用的正确使用


#####最后告诫一点：尽量不要在应用中调用System.gc(); 如果调用了System.gc()可能会为系统性能带来严重的波动，即便调用System.gc()系统也未必立即响应去执行垃圾回收。





	
