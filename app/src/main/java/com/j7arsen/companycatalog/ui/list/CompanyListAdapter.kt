package com.j7arsen.companycatalog.ui.list

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.ProgressBar
import androidx.appcompat.widget.AppCompatTextView
import androidx.recyclerview.widget.RecyclerView
import com.j7arsen.companycatalog.R
import com.j7arsen.companycatalog.utils.ImageLoadedCallback
import com.j7arsen.data.api.BASE_URL
import com.j7arsen.domain.model.CompanyModel
import com.squareup.picasso.Picasso

class CompanyListAdapter : RecyclerView.Adapter<CompanyListAdapter.ViewHolder>() {

    private var companyList: MutableList<CompanyModel> = mutableListOf()

    private var onItemClickListener : OnItemClickListener? = null

    fun setOnItemClickListener(onItemClickListener: OnItemClickListener){
        this.onItemClickListener = onItemClickListener
    }

    fun setData(companyList: List<CompanyModel>) {
        this.companyList = companyList.toMutableList()
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.item_company,
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val company = companyList[position];
        holder.bind(company)
    }

    override fun getItemCount() = companyList.size

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private val tvNCompanyTitle: AppCompatTextView = itemView.findViewById(R.id.tv_company_title);
        private val pbCompanyImgLoad: ProgressBar = itemView.findViewById(R.id.pb_company_img_load);
        private val ivCompanyImg: ImageView = itemView.findViewById(R.id.iv_company_img);

        fun bind(companyModel: CompanyModel) {
            tvNCompanyTitle.text = companyModel.name
            Picasso.get()
                .load(BASE_URL + companyModel.img)
                .into(
                    ivCompanyImg,
                    ImageLoadedCallback(
                        ivCompanyImg,
                        pbCompanyImgLoad
                    )
                )
            this.itemView.setOnClickListener{ onItemClickListener?.onItemClick(companyModel)}
        }
    }

    public interface OnItemClickListener {

        fun onItemClick(companyModel: CompanyModel)

    }

}