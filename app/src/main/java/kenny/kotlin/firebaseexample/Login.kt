package kenny.kotlin.firebaseexample

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_login.*

class Login : AppCompatActivity() {

    //Creamos el listener de autenticacion a  firebase
    var mAuthListenr : FirebaseAuth.AuthStateListener? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        // instanciamos el listener cuando se crea la actividad
        mAuthListenr = FirebaseAuth.AuthStateListener(object : FirebaseAuth.AuthStateListener, (FirebaseAuth) -> Unit {
            override fun invoke(p1: FirebaseAuth) {
            }

            override fun onAuthStateChanged(p0: FirebaseAuth) {
                var user = p0.getCurrentUser()
                if (user != null){
                    Log.i("SESION","secion iniciada  con email : ${user.email}")
                }else{
                    Log.i("SESION","secion cerrada 1 ")
                }
            }
        })

        t_signup.setOnClickListener{
            var goSignUp = Intent(this, SignUpActivity::class.java)
            startActivity(goSignUp)
        }

        btn_sing.setOnClickListener {
            FirebaseAuth.getInstance().signInWithEmailAndPassword(e_username.text.toString(),e_pass.text.toString()).addOnCompleteListener(object : OnCompleteListener<AuthResult> {
                override fun onComplete(p0: Task<AuthResult>) {
                    if (p0.isSuccessful){
                        var goSignUp = Intent(applicationContext, MainActivity::class.java)
                        startActivity(goSignUp)
                        Log.i("SESION","secion iniciada  ")
                    }else{
                        Log.i( "SESION","secion cerrada 2"+ p0.exception)
                    }
                }

            })
        }
    }


    //seteamos nuestro listener al Api cuando se inicia el activity
    override fun onStart() {
        super.onStart()
        FirebaseAuth.getInstance().addAuthStateListener(mAuthListenr!!)
    }

    // quitamos el listener cuando se sale del activity
    override fun onStop() {
        super.onStop()
        if (mAuthListenr != null){
            FirebaseAuth.getInstance().removeAuthStateListener(mAuthListenr!!)
        }
    }
}
