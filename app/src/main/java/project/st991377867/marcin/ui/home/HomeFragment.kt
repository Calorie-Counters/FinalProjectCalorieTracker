package project.st991377867.marcin.ui.home


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import project.st991377867.marcin.adapters.ItemAdapter
import project.st991377867.marcin.databinding.FragmentHomeBinding
import project.st991377867.marcin.ui.item.ItemViewModel


class HomeFragment : Fragment() {


    companion object {
        fun newInstance() = HomeFragment()
    }

    private val viewModel: ItemViewModel by viewModels()
    private lateinit var binding: FragmentHomeBinding
    //private lateinit var itemRecyclerView: ItemRecyclerView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentHomeBinding.inflate(inflater)

        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //val list: LiveData<List<Item>> = viewModel.getDummyItems()
        val adapter = ItemAdapter{
            val action = HomeFragmentDirections.actionNavHomeToItemFragment(it.id)
            this.findNavController().navigate(action)}

        viewModel.requestItems()
        viewModel.itemListLiveData.observe(viewLifecycleOwner) {
            adapter.submitList(it)
        }

        binding.itemRecyclerView.adapter = adapter
        binding.itemRecyclerView.layoutManager = LinearLayoutManager(this.context)
        binding.itemRecyclerView.setHasFixedSize(true)
        binding.btnAddItem.setOnClickListener {
            val action = HomeFragmentDirections.actionNavHomeToItemFragment()
            this.findNavController().navigate(action)
        }
    }


















//    private var _binding: FragmentHomeBinding? = null
//    private val binding get() = _binding!!
//    val fireStoreDatabase = FirebaseFirestore.getInstance()
//    val itemArrayList = ArrayList<Item>();
//
//
//    companion object {
//        fun newInstance() = HomeFragment()
//    }
//
//    private lateinit var viewModel: HomeViewModel
//
//    override fun onCreateView(
//        inflater: LayoutInflater, container: ViewGroup?,
//        savedInstanceState: Bundle?
//    ): View? {
////        return inflater.inflate(R.layout.fragment_home, container, false)
//        _binding = FragmentHomeBinding.inflate(inflater, container, false)
//
//
//        return binding.root
//    }
//
//    override fun onActivityCreated(savedInstanceState: Bundle?) {
//        super.onActivityCreated(savedInstanceState)
//        viewModel = ViewModelProvider(this).get(HomeViewModel::class.java)
//        // TODO: Use the ViewModel
//    }
////
//    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
//        super.onViewCreated(view, savedInstanceState)
//
//        //binding.recycleView.layoutManager = LinearLayoutManager(activity);
//        binding.itemRecyclerView.layoutManager = LinearLayoutManager(this.context)
//        binding.itemRecyclerView.setHasFixedSize(true);
//
//        //binding.recycleView.adapter = ItemAdapter(this.itemArrayList);
//
//
//
//
//
//        binding.btnAddItem.setOnClickListener {
//            val action = HomeFragmentDirections.actionNavHomeToItemFragment()
//            this.findNavController().navigate(action)
//        }
//
//        binding.btnEdit.setOnClickListener {
////            val action = HomeFragmentDirections.actionNavHomeToItemDetailFragment()
////            this.findNavController().navigate(action)
////            fireStoreDatabase.collection("items")
////                .get()
////                .addOnCompleteListener{
////                    val result: StringBuffer = StringBuffer()
////                    if(it.isSuccessful){
////                        for(document in it.result){
////                            result.append(document.data.getValue("itemName"))
////                        }
////                    }
////                }
//
//        }
//
//    }


}