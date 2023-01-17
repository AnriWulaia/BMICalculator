package com.example.finaluri

import android.content.Context
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.google.firebase.database.*

class ResultFragment : Fragment() {

    private lateinit var dbRef : DatabaseReference

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_result, container, false)
        val nextBtn : TextView = view.findViewById(R.id.buttonnext)
        val bmi : TextView = view.findViewById(R.id.bmishow)
        val image :ImageView = view.findViewById(R.id.image1)
        val lastBmi : TextView = view.findViewById(R.id.LastBmi)
        val showButton : TextView = view.findViewById(R.id.show)
        val under : ImageView = view.findViewById(R.id.under)
        val normal : ImageView = view.findViewById(R.id.normal)
        val over : ImageView = view.findViewById(R.id.over)


        val data = arguments
        val value = data?.getString("BMI")
        bmi.text = value
        val val1 = value?.toFloat()
        if (val1 != null) {
            if(val1 < 18.5 || val1 > 25){
                bmi.setTextColor(Color.parseColor("#FF0000"))
                image.setImageResource(R.drawable.very_dissatisfied)
                view.setBackgroundResource(R.drawable.bad_back)
            }else{
                bmi.setTextColor(Color.parseColor("#008000"))
                image.setImageResource(R.drawable.very_satisfied)
                view.setBackgroundResource(R.drawable.good_back)
            }
        }
        if (val1 != null) {
            if (val1 < 18.5){
                under.setImageResource(R.drawable.arrow)
            }else if(val1>18.5 && val1!! < 24.9){
                normal.setImageResource(R.drawable.arrow)
            }else{
                over.setImageResource(R.drawable.arrow)
            }
        }



        dbRef = FirebaseDatabase.getInstance().getReference("bmi")


        nextBtn.setOnClickListener(){
            saveBmi()
        }

        dbRef.orderByKey().limitToLast(1).addValueEventListener(object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                for (ds in snapshot.children){
                    showButton.setOnClickListener(){
                        val bmi1 = ds.child("bmi").value.toString()
                        lastBmi.text = bmi1
                    }
                }
            }
            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(activity, "Error", Toast.LENGTH_SHORT).show()
            }
        })

        return view
    }

    private fun saveBmi(){
        val bmi = dbRef.push().key!!
        val data = arguments
        val value = data?.getString("BMI")
        val bmi1 = Bmi(value)
        dbRef.child(bmi).setValue(bmi1)
            .addOnCompleteListener{
                Toast.makeText(activity, "Saved", Toast.LENGTH_SHORT).show()
            }
    }
}