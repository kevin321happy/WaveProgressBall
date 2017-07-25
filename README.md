# WaveProgressBall
一个水波纹进度球的控件,水波纹的有颜色渐变的效果
主要通过给水波纹的画笔设置颜色

 //设置颜色渐变的效果
 
        Shader shader = new LinearGradient(100, 100, 100, 300, Color.parseColor(mWaveColorEnd),
                Color.parseColor(mWaveColor), Shader.TileMode.CLAMP);

        mWavePaint.setShader(shader);


![image](https://github.com/kevin321happy/WaveProgressBall/blob/master/app/gif/%E6%B0%B4%E6%B3%A2%E7%BA%B9%E5%8F%8A%E5%A4%96%E7%8E%AF%E7%9A%84%E6%B8%90%E5%8F%98%E6%95%88%E6%9E%9C.gif)

这是一个测试的GIF

![image](https://github.com/kevin321happy/WaveProgressBall/blob/master/app/gif/test.gif)

