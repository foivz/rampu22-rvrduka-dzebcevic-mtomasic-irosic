import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import hr.foi.rampu.stanarko.R
import hr.foi.rampu.stanarko.database.FlatsDAO
import hr.foi.rampu.stanarko.entities.Malfunction

class MalfunctionAdapter(private val malfunctions: List<Malfunction>) :
    RecyclerView.Adapter<MalfunctionAdapter.MalfunctionViewHolder>() {
    class MalfunctionViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private var malfunctionDesc : TextView
        private var malfunctionAdres : TextView
        private var malfunctionStatus : TextView
        private var fixed: ImageButton


        init {
            malfunctionDesc = itemView.findViewById(R.id.malfunction_name)
            malfunctionAdres = itemView.findViewById(R.id.malfunction_address)
            malfunctionStatus = itemView.findViewById(R.id.malfunction_status)
            fixed = itemView.findViewById(R.id.ib_fix_mal)

        }

        fun bind(malfunction: Malfunction) {
            malfunctionDesc.text = malfunction.description
            malfunctionAdres.text = malfunction.flat!!.address
            malfunctionStatus.text = malfunction.status.toString()


            fixed.setOnClickListener{


                Log.d("DADA", "BOBOBBOBOBO")
            }
        }
    }



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MalfunctionViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.malfunction_item, parent, false)
        return MalfunctionViewHolder(view)
    }

    override fun onBindViewHolder(holder: MalfunctionViewHolder, position: Int) {
        val malfunction = malfunctions[position]
        holder.bind(malfunction)
    }

    override fun getItemCount() = malfunctions.size


}

