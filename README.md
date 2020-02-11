# JUtils
Android 开发工具类

## 使用方式
### Step 1. Add the JitPack repository to your build file
```
allprojects {
    repositories {
        ...
        maven { url 'https://jitpack.io' }
    }
}
```

### Step 2. Add the dependency
```
dependencies {
    api 'com.github.ltym2016:JUtils:1.0.5'
}
```


### Step 3. init
在Application的onCreate里初始化
```java
@Override
public void onCreate() {
    super.onCreate();
    Utils.init(this);
}
```
