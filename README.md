# SimpleGridLayout
一种metro风格UI的实现，用起来很简便。

# 截图
![此处输入图片的描述][1]

# 使用方法

```xml
<com.ntrees.widget.SimpleGridLayout
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:layout_gravity="center"
        app:cell_height="100dp"
        app:cell_width="100dp"
        app:space_horizental="10dp"
        app:space_vertical="10dp" >

        <ImageView
            android:layout_width="wrap_content"
            android:layout_height="wrap_content"
            android:background="#66ffffff"
            android:src="@drawable/ic_launcher"
            app:cell_spanX="1"
            app:cell_spanY="1"
            app:cell_x="1"
            app:cell_y="0" />

        <ImageView
            android:layout_width="wrap_content"
            android:layout_height="wrap_content"
            android:background="#66ffffff"
            android:src="@drawable/ic_launcher"
            app:cell_spanX="1"
            app:cell_spanY="2"
            app:cell_x="0"
            app:cell_y="0" />

        <ImageView
            android:layout_width="wrap_content"
            android:layout_height="wrap_content"
            android:background="#66ffffff"
            android:src="@drawable/ic_launcher"
            app:cell_spanX="2"
            app:cell_spanY="1"
            app:cell_x="1"
            app:cell_y="1" />

        <ImageView
            android:layout_width="wrap_content"
            android:layout_height="wrap_content"
            android:background="#66ffffff"
            android:src="@drawable/ic_launcher"
            app:cell_spanX="1"
            app:cell_spanY="1"
            app:cell_x="2"
            app:cell_y="0" />
    </com.ntrees.widget.SimpleGridLayout>
```

#自定义属性
cell_x
cell_y
:	分别为网格坐标

cell_spanX
cell_spanY
:	分别为x方向和y方向的宽度

cell_width
cell_height
:	分别为单元格的宽和高

space_horizental
space_vertical
:	分别问横向和纵向的网格间隙。



**Enjoy it！**

  [1]: https://raw.githubusercontent.com/ntrees/SimpleGridLayout/master/doc/shutcut.jpg
  
