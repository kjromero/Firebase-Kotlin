package kenny.kotlin.firebaseexample

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import com.google.firebase.database.*
import kenny.kotlin.firebaseexample.model.Car
import kenny.kotlin.firebaseexample.model.Constans
import kotlinx.android.synthetic.main.activity_main.*

data class Cursos(val nombre: String, val url: String)

class MainActivity : AppCompatActivity() {

    val react = Cursos("React","react")
    val kotlin = Cursos("Kotlin","kotlin")
    var cursoActual = react.copy()

    var i : Int = 1
    var mDatabaseRef: DatabaseReference? = null
    var carsReference: DatabaseReference? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        mDatabaseRef = FirebaseDatabase.getInstance().reference.child("appname")
        carsReference = FirebaseDatabase.getInstance().reference.child("cars")

        val fBtn = findViewById(R.id.btn) as Button
        fBtn.setOnClickListener{
            view -> switchCurso(cursoActual)
            var mustang = Car("Ford","1968","3600","shelby leonore")
            carsReference?.child(Constans.CAR_REFERENCE)?.push()?.setValue(mustang)
        }

        carsReference?.child(Constans.CAR_REFERENCE)?.addValueEventListener(object : ValueEventListener{
            override fun onCancelled(p0: DatabaseError?) {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }

            override fun onDataChange(p0: DataSnapshot?) {
                 var peugeot = Car(p0?.child("marca").toString(),p0?.child("modelo").toString(),p0?.child("motor").toString(),p0?.child("nombre").toString())
                    Log.i("CAR",peugeot.marca)
            }
        })


        mDatabaseRef?.addValueEventListener(object : ValueEventListener {
            override fun onCancelled(p0: DatabaseError?) {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }

            override fun onDataChange(p0: DataSnapshot?) {
                if (p0 == null) return
                firebaseText.setText(p0.getValue().toString())
            }
        })
    }


    fun switchCurso(curso : Cursos){
        //cursoActual = curso.copy()
        when(curso.url){
            "react" -> cursoActual = kotlin.copy()
            "kotlin" -> cursoActual = react.copy()
            else -> print("Esto nunca va a pasar")
        }
        verEnPantalla("Curso de ${cursoActual.nombre} en platzi.com/${cursoActual.url}")
    }


    fun verEnPantalla( text : String){
        ftext.setText(text)
    }
}
