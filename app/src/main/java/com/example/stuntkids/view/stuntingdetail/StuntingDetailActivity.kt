package com.example.stuntkids.view.stuntingdetail

import android.os.Bundle
import android.text.SpannableString
import android.text.style.LeadingMarginSpan
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.text.HtmlCompat
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.stuntkids.R
import com.example.stuntkids.adapter.ArticleAdapter
import com.example.stuntkids.adapter.FoodAdapter
import com.example.stuntkids.data.model.PredictModel
import com.example.stuntkids.databinding.ActivityStuntingDetailBinding
import com.example.stuntkids.model.Article
import com.example.stuntkids.view.stuntingresult.StuntingResultActivity.Companion.EXTRA_RESULT

class StuntingDetailActivity : AppCompatActivity() {
    private lateinit var binding: ActivityStuntingDetailBinding

    private val predictModel by lazy {
        PredictModel.fromRequestText(intent.getStringExtra(EXTRA_RESULT)!!)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityStuntingDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setViews()
        setListeners()
    }

    private fun setViews() {
        binding.apply {
            setStunting(isStunting(predictModel?.result), predictModel?.result ?: "")
            tvName.setText(predictModel?.name)
            tvAge.setText("Age (Months): ${predictModel?.age} (${((predictModel?.age ?: 0) * 12)} Months)")
            tvGender.setText(
                when (predictModel?.gender) {
                    0 -> "Kid's Gender: Laki-laki"
                    1 -> "Kid's Gender: Perempuan"
                    else -> "Kid's Gender: Tidak diketahui"
                }
            )
            tvHeight.setText("Kid's Body Height (cm): ${predictModel?.height} cm")

            val stuntingCauses = """
    • Asupan gizi yang tidak memadai atau malnutrisi dalam jangka panjang dapat menyebabkan stunting
    • Kondisi kesehatan dan gizi ibu sebelum dan saat kehamilan, serta setelah persalinan dapat mempengaruhi pertumbuhan janin.
    • Kurangnya akses terhadap sanitasi dan air bersih dapat menyebabkan stunting
    • Pola asuh yang kurang baik, terutama dalam pemberian makan kepada anak, dapat menyebabkan stunting
    • Jarak kelahiran anak yang terlalu dekat dapat menyebabkan stunting.
""".trimIndent()

            val spannableString = SpannableString(stuntingCauses).apply {
                setSpan(LeadingMarginSpan.Standard(20, 40), 0, length, 0)
            }

            tvStuntingCauses.text = spannableString

            val foodAdapter = FoodAdapter(getArticles(predictModel?.result ?: "", predictModel?.age ?: 0))
            val articleAdapter =
                ArticleAdapter(getArticles(predictModel?.result ?: "", predictModel?.age ?: 0))

            rvFoods.apply {
                adapter = foodAdapter
                layoutManager = LinearLayoutManager(this@StuntingDetailActivity)
            }

            tvStuntingWarning.text = HtmlCompat.fromHtml(
                getString(R.string.stunting_warning),
                HtmlCompat.FROM_HTML_MODE_COMPACT
            )

            rvArticles.apply {
                adapter = articleAdapter
                layoutManager = LinearLayoutManager(this@StuntingDetailActivity)
            }
        }
    }

    private fun setStunting(isStunting: Boolean, result: String) {
        binding.apply {
            layoutStuntingPositive.isVisible = isStunting
            layoutStuntingNegative.isVisible = !isStunting

            tvStatus.backgroundTintList = ContextCompat.getColorStateList(
                this@StuntingDetailActivity,
                if (isStunting) R.color.red else R.color.green
            )

            tvStatus.text = result

            tvStuntingFlag.isVisible = isStunting
        }
    }

    private fun setListeners() {
        binding.apply {
            btnBack.setOnClickListener { finish() }
            btnConfirm.setOnClickListener { finish() }
        }
    }

    private fun isStunting(result: String?): Boolean {
        return when (result) {
            "Stunted" -> true
            "Normal" -> false
            "Severly Stunted" -> true
            "Tinggi" -> false
            else -> false
        }
    }

    private fun getArticles(result: String?, age: Int): List<Article> {
        return when (result?.lowercase()) {
            "normal" -> {
                when {
                    age <= 6 -> listOf(
                        Article("Sesuai kebutuhan bayi", "ASI Eksklusif", "", url = "")
                    )

                    age in 7..11 -> listOf(
                        Article(
                            "Frekuensi Makan",
                            "2-3 kali MPASI, 1-2 Makanan Selingan + ASI",
                            "" ,
                            url = ""
                        ),
                        Article(
                            "Protein 30-45 gr",
                            "- ikan\n- daging\n- ayam\n- telur 1/2 butir",
                            "",
                            url = ""
                        ),
                        Article(
                            "Lemak 30 gr",
                            "minyak Sehat (minyak ikan atau minyak kedelai)",
                            "",
                            url = ""
                        ),
                        Article("Karbohidrat 125 ml", "- ubi\n- kentang\n- nasi", "", url = ""),
                        Article("Sayur 100 gr", "- bayam\n- labu kuning\n- brokoli\n- wortel", "", url = ""),
                        Article("Buah 50 gr", "- pisang\n- jeruk\n- kiwi", "", url = ""),
                        Article("Susu 250 ml", "- ASI\n- Susu Formula Pertumbuhan", "", url = "")
                    )

                    else -> listOf(
                        Article("Frekuensi Makan", "3 Makanan Utama, 1 Makanan Selingan", "", url = ""),
                        Article("Protein 26 gr", "- ikan\n- daging\n- ayam\n- telur 1 butir", "", url = ""),
                        Article(
                            "Lemak 35-40 gr",
                            "minyak Sehat (minyak ikan atau minyak kedelai)",
                            "",
                            url = ""
                        ),
                        Article("Karbohidrat 150 ml", "- ubi\n- kentang\n- nasi", "", url = ""),
                        Article("Sayur 200 gr", "- bayam\n- labu kuning\n- brokoli\n- wortel", "", url = ""),
                        Article("Buah 200 gr", "- pisang\n- jeruk\n- kiwi", "", url = ""),
                        Article(
                            "Susu 250 ml",
                            "- Susu Formula Pertumbuhan\n- Susu Sapi UHT\n- Susu Kedelai",
                            "",
                            url = ""
                        )
                    )
                }
            }

            "severely stunted" -> {
                when {
                    age <= 6 -> listOf(
                        Article("Sesuai kebutuhan bayi", "ASI Eksklusif", "", url = "")
                    )

                    age in 7..11 -> listOf(
                        Article(
                            "Frekuensi Makan",
                            "3-4 kali MPASI, 1-2 Makanan Selingan + ASI",
                            "",
                            url = ""
                        ),
                        Article(
                            "Protein 45-60 gr",
                            "- ikan\n- daging\n- ayam\n- telur 1 butir",
                            "",
                            url = ""
                        ),
                        Article(
                            "Lemak 40 gr",
                            "minyak Sehat (minyak ikan atau minyak kedelai)",
                            "",
                            url = ""
                        ),
                        Article("Karbohidrat 125 ml", "- ubi\n- kentang\n- nasi", "", url = ""),
                        Article("Sayur 100 gr", "- bayam\n- labu kuning\n- brokoli\n- wortel", "", url = ""),
                        Article("Buah 50-100 gr", "- pisang\n- jeruk\n- kiwi", "", url = ""),
                        Article("Susu 300-350 ml", "- ASI\n- Susu Formula Pertumbuhan", "", url = "")
                    )

                    else -> listOf(
                        Article("Frekuensi Makan", "3 Makanan Utama, 2 Makanan Selingan", "", url = ""),
                        Article(
                            "Protein 26-30 gr",
                            "- ikan\n- daging\n- ayam\n- telur 1 butir",
                            "",
                            url = ""
                        ),
                        Article(
                            "Lemak 40 gr",
                            "minyak Sehat (minyak ikan atau minyak kedelai)",
                            "",
                            url = ""
                        ),
                        Article("Karbohidrat 150-160 ml", "- ubi\n- kentang\n- nasi", "", url = ""),
                        Article("Sayur 200 gr", "- bayam\n- labu kuning\n- brokoli\n- wortel", "", url = ""),
                        Article("Buah 200 gr", "- pisang\n- jeruk\n- kiwi", "", url = ""),
                        Article(
                            "Susu 350 ml",
                            "- Susu Formula Pertumbuhan\n- Susu Sapi UHT\n- Susu Kedelai",
                            "",
                            url = ""
                        )
                    )
                }
            }

            "stunted" -> {
                when {
                    age <= 6 -> listOf(
                        Article("Sesuai kebutuhan bayi", "ASI Eksklusif", "", url = "")
                    )

                    age in 7..11 -> listOf(
                        Article("Frekuensi Makan", "3 kali MPASI, 1-2 Makanan Selingan + ASI", "", url = ""),
                        Article(
                            "Protein 45 gr",
                            "- ikan\n- daging\n- ayam\n- telur 1/2 - 1 butir",
                            "",
                            url = ""
                        ),
                        Article(
                            "Lemak 30-40 gr",
                            "minyak Sehat (minyak ikan atau minyak kedelai)",
                            "",
                            url = ""
                        ),
                        Article("Karbohidrat 125 ml", "- ubi\n- kentang\n- nasi", "", url = ""),
                        Article("Sayur 100 gr", "- bayam\n- labu kuning\n- brokoli\n- wortel", "", url = ""),
                        Article("Buah 50 gr", "- pisang\n- jeruk\n- kiwi", "", url = ""),
                        Article("Susu 250 ml", "- ASI\n- Susu Formula Pertumbuhan", "", url = "")
                    )

                    else -> listOf(
                        Article("Frekuensi Makan", "3 Makanan Utama, 1-2 Makanan Selingan", "", url = ""),
                        Article(
                            "Protein 20-26 gr",
                            "- ikan\n- daging\n- ayam\n- telur 1 butir",
                            "",
                            url = ""
                        ),
                        Article(
                            "Lemak 35-40 gr",
                            "minyak Sehat (minyak ikan atau minyak kedelai)",
                            "",
                            url = ""
                        ),
                        Article("Karbohidrat 150 ml", "- ubi\n- kentang\n- nasi", "", url = ""),
                        Article("Sayur 200 gr", "- bayam\n- labu kuning\n- brokoli\n- wortel", "", url = ""),
                        Article("Buah 200 gr", "- pisang\n- jeruk\n- kiwi", "", url = ""),
                        Article(
                            "Susu 250-300 ml",
                            "- Susu Formula Pertumbuhan\n- Susu Sapi UHT\n- Susu Kedelai",
                            "",
                            url = ""
                        )
                    )
                }
            }

            "tinggi" -> {
                when {
                    age <= 6 -> listOf(
                        Article("Sesuai kebutuhan bayi", "ASI Eksklusif", "", url = "")
                    )

                    age in 7..11 -> listOf(
                        Article("Frekuensi Makan", "3 kali MPASI, 1-2 Makanan Selingan + ASI", "", url = ""),
                        Article(
                            "Protein 60 gr",
                            "- ikan\n- daging\n- ayam\n- telur 1/2 - 1 butir",
                            "",
                            url = ""
                        ),
                        Article(
                            "Lemak 40-45 gr",
                            "minyak Sehat (minyak ikan atau minyak kedelai)",
                            "",
                            url = ""
                        ),
                        Article("Karbohidrat 150 ml", "- ubi\n- kentang\n- nasi", "", url = ""),
                        Article("Sayur 100 gr", "- bayam\n- labu kuning\n- brokoli\n- wortel", "", url = ""),
                        Article("Buah 200 gr", "- pisang\n- jeruk\n- kiwi", "", url = ""),
                        Article("Susu 300-350 ml", "- ASI\n- Susu Formula Pertumbuhan", "", url = "")
                    )

                    else -> listOf(
                        Article("Frekuensi Makan", "3 Makanan Utama, 2 Makanan Selingan", "", url = ""),
                        Article(
                            "Protein 30-35 gr",
                            "- ikan\n- daging\n- ayam\n- telur 1 butir",
                            "",
                            url = ""
                        ),
                        Article(
                            "Lemak 40-45 gr",
                            "minyak Sehat (minyak ikan atau minyak kedelai)",
                            "",
                            url = ""
                        ),
                        Article("Karbohidrat 160-180 ml", "- ubi\n- kentang\n- nasi", "", url = ""),
                        Article("Sayur 200 gr", "- bayam\n- labu kuning\n- brokoli\n- wortel", "", url = ""),
                        Article("Buah 200 gr", "- pisang\n- jeruk\n- kiwi", "", url = ""),
                        Article(
                            "Susu 350 ml",
                            "- Susu Formula Pertumbuhan\n- Susu Sapi UHT\n- Susu Kedelai",
                            "",
                            url = ""
                        )
                    )
                }
            }

            else -> listOf()
        }
    }
}