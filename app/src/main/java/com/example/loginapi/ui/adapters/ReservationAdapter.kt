package com.example.loginapi.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.loginapi.databinding.ReservationItemLayoutBinding
import com.example.loginapi.models.dto.ReservationDTO

class ReservationAdapter(
    private val reservationList: ArrayList<ReservationDTO>,
    private val listener: OnReservationClickListener? = null
) : RecyclerView.Adapter<ReservationAdapter.ReservationViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ReservationViewHolder {
        val binding = ReservationItemLayoutBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return ReservationViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ReservationViewHolder, position: Int) {
        val reservation = reservationList[position]
        holder.bind(reservation, listener)
    }

    override fun getItemCount(): Int = reservationList.size

    fun updateData(reservations: List<ReservationDTO>?) {
        reservations?.let {
            reservationList.clear()
            reservationList.addAll(it)
            notifyDataSetChanged()
        }
    }

    class ReservationViewHolder(private val binding: ReservationItemLayoutBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(reservation: ReservationDTO, listener: OnReservationClickListener?) {
            binding.apply {
                txtDate.text = reservation.date
                txtTime.text = reservation.time
                lblReservationPeople.text = reservation.people.toString()
                txtStatus.text = reservation.status
                root.setOnClickListener { listener?.onReservationClick(reservation) }
            }
        }
    }

    interface OnReservationClickListener {
        fun onReservationClick(reservation: ReservationDTO)
    }
}
