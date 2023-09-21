package chenchen.engine.basiclayout.demo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import chenchen.engine.basiclayout.demo.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    val binding by lazy { ActivityMainBinding.inflate(layoutInflater) }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        binding.sfl.setOnClickListener {
            it.invalidate()
        }
    }
}