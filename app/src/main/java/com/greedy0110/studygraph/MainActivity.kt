package com.greedy0110.studygraph

import android.animation.Animator
import android.animation.ValueAnimator
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.greedy0110.circlecounterview.CircleCounterView
import kotlinx.android.synthetic.main.activity_main.*
import kotlin.math.roundToInt
import kotlin.random.Random

class MainActivity : AppCompatActivity() {
    private val adapter = Adapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        playAnimation.setOnClickListener {
            adapter.notifyDataSetChanged()
        }

        trashList.adapter = adapter
        trashList.layoutManager = GridLayoutManager(this, 5)
        adapter.notifyDataSetChanged()
    }

    private inner class Adapter : RecyclerView.Adapter<CircleViewHolder>() {
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CircleViewHolder {
            return CircleViewHolder(layoutInflater.inflate(R.layout.trash_item, parent, false))
        }

        override fun getItemCount(): Int {
            return 100
        }

        override fun onBindViewHolder(holder: CircleViewHolder, position: Int) {
            holder.animate()
        }
    }

    private inner class CircleViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private var animator: Animator? = null
        private val view: CircleCounterView = view.findViewById(R.id.circleCounterView)

        fun animate() {
            val animatorDuration = Random.nextLong(1000L, 10000L)
            animator = ValueAnimator.ofFloat(0f, 360f).apply {
                duration = animatorDuration
                addUpdateListener {
                    (it.animatedValue as? Float)?.let { value ->
                        view.angle = value
                        view.text = "${((value) / 360f * animatorDuration / 1000f).roundToInt()} 초"
                    }
                }
                start()
            }
        }
    }
}
