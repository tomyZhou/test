package com.example.test

import android.app.Activity
import android.content.ComponentName
import android.content.Intent
import android.content.pm.PackageManager
import android.content.pm.ResolveInfo
import android.graphics.Color
import android.graphics.Outline
import android.graphics.Paint
import android.graphics.Path
import android.os.Build
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.view.ViewOutlineProvider
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import com.example.event.AspectAnalyze
import com.example.event.Param
import com.google.android.material.shape.*

class MainActivity : Activity() {

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        var image13 = findViewById<ImageView>(R.id.image13)
        image13.outlineProvider = object : ViewOutlineProvider() {
            override fun getOutline(view: View, outline: Outline) {
                outline.setRoundRect(0, 0, view.width, view.height, 32f)
                outline.setOval(0, 0, view.width, view.height)
            }
        }
        image13.clipToOutline = true


        var image14 = findViewById<ImageView>(R.id.image14)
        image14.outlineProvider = object : ViewOutlineProvider() {
            override fun getOutline(view: View, outline: Outline) {
                val path = Path()
                view.elevation = 4f
                path.moveTo(-20f, -20f)
                path.lineTo(-20f, view.height.toFloat() + 20)
                path.lineTo(view.width.toFloat() + 20, view.height.toFloat() + 20)
                path.lineTo(view.width.toFloat() + 20, -20f)
                path.close()
                outline.setConvexPath(path)
            }
        }
        image14.clipToOutline = true


        val text1 = findViewById<TextView>(R.id.tv1)
        val shapePathModel = ShapeAppearanceModel.builder()
            .setAllCorners(RoundedCornerTreatment())
            .setAllCornerSizes(16f)
            .setRightEdge(object : TriangleEdgeTreatment(18f, false) {
                override fun getEdgePath(
                    length: Float,
                    center: Float,
                    interpolation: Float,
                    shapePath: ShapePath
                ) {
                    super.getEdgePath(length, 40f, interpolation, shapePath)
                }
            })
            .build()
        val backgroundDrawable = MaterialShapeDrawable(shapePathModel).apply {
            setTint(Color.parseColor("#0033aa"))
            paintStyle = Paint.Style.FILL
        }
        (text1.parent as? ViewGroup)?.clipChildren = false
        text1.background = backgroundDrawable

    }


    override fun onResume() {
        super.onResume()
        PrintTimeUtil.time("onResume")

        KotlinSample().callMethods()

        //动态修改图标
        // if(日期 == 节日){
         changeLuncher("com.example.test.newsLuncherActivity")
        // }


    }

    private fun changeLuncher(name: String) {
        var componentName = ComponentName(this, name)
        packageManager.setComponentEnabledSetting(
            componentName,
            PackageManager.COMPONENT_ENABLED_STATE_DISABLED,
            PackageManager.DONT_KILL_APP
        );
        packageManager.setComponentEnabledSetting(
            ComponentName(this, name),
            PackageManager.COMPONENT_ENABLED_STATE_ENABLED, PackageManager.DONT_KILL_APP
        );

    }


    @AspectAnalyze(params = ["{aa}", "{bb}"])
    private fun hello(@Param("aa") arg1: String, @Param("bb") arg2: String) {
        println(arg1 + arg2)
    }
}