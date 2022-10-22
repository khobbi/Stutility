package com.edon.stutility

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.edon.stutility.adapters.OtherSitesAdapter
import com.edon.stutility.databinding.ActivityOtherElearningBinding
import com.edon.stutility.models.Site

class OtherELearningActivity : AppCompatActivity() {
    lateinit var bd: ActivityOtherElearningBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bd = ActivityOtherElearningBinding.inflate(layoutInflater)
        setContentView(bd.root)

        //create data set to be displayed
        val dataSet = fillSites()
        //create adapter instance and pass the dataset to it
        val adapter = OtherSitesAdapter(dataSet)
        //set a layout manager for the recyclerview
        bd.recOtherSites.layoutManager = LinearLayoutManager(this)
        //add the adapter to the recycler view layout
        bd.recOtherSites.adapter = adapter
    }

    //create an ArrayList of Site(s) and return it to the variable in the
    //onCreate
    private fun fillSites(): ArrayList<Site> {
        val addressList = ArrayList<Site>()
        addressList.add(Site("Academia", "https://academia.edu/", R.drawable.academia))
        addressList.add(Site("Academic Info", "https://www.academicinfo.net/", R.drawable.aca_info))
        addressList.add(Site("Alison", "https://alison.com", R.drawable.alison))
        addressList.add(Site("Baidu Scholar", "http://research.baidu.com/", R.drawable.baidu))
        addressList.add(Site("Bielefeld Academic Search Engine (BASE)", "https://www.base-search.net/", R.drawable.base))
        addressList.add(Site("BookBoon", "https://bookboon.com", R.drawable.bookboon))
        addressList.add(Site("Calameo books", "https://en.calameo.com/books/", R.drawable.calameo))
        addressList.add(Site("Calameo PDFs", "https://calameo.pdf-downloader.com/index.php", R.drawable.calameo))
        addressList.add(Site("CodeAcademy", "https://www.codeacademy.com/", R.drawable.codecademy_icon))
        addressList.add(Site("CORE", "https://core.ac.uk", R.drawable.core))
        addressList.add(Site("Coursera", "https://www.coursera.org/", R.drawable.coursera_icon))
        addressList.add(Site("Edx", "https://www.edx.org/", R.drawable.edx))
        addressList.add(Site("Free Computer Books", "https://freecomputerbooks.com", R.drawable.freecomputerbooks))
        addressList.add(Site("Free eBooks", "https://www.free-ebooks.net", R.drawable.free_ebooks))
        addressList.add(Site("GitHub", "https://github.com/git", R.drawable.github))
        addressList.add(Site("Google", "https://google.com/", R.drawable.google_logo_new_icon))
        addressList.add(Site("Google Books", "https://books.google.com/", R.drawable.google_logo_new_icon))
        addressList.add(Site("Google Scholar", "https://scholar.google.com/", R.drawable.google_logo_new_icon))
        addressList.add(Site("Internet Archive", "https://archive.org", R.drawable.archive))
        addressList.add(Site("JSTOR", "https://www.jstor.org/", R.drawable.jstor))
        addressList.add(Site("KhanAcademy", "https://www.khanacademy.org/", R.drawable.khanacademy_icon))
        addressList.add(Site("Library Genesis", "https://gen.lib.rus.ec", R.drawable.libgen))
        addressList.add(Site("Lynda", "https://www.lynda.com/", R.drawable.lynda_com))
        addressList.add(Site("Many Books", "https://manybooks.net", R.drawable.manybooks)) //srv.
        addressList.add(Site("Microsoft Academic", "https://academic.microsoft.com/home", R.drawable.msacademic))
        addressList.add(Site("Obokoo", "https://www.obokoo.com", R.drawable.obokoo))
        addressList.add(Site("OpenLibrary", "https://openlibrary.org", R.drawable.open_lib))
        addressList.add(Site("PdfDrive", "https://pdfdrive.net", R.drawable.pdfdrive))
        addressList.add(Site("Refseek", "https://www.refseek.com/", R.drawable.refseek))
        addressList.add(Site("Saylor", "https://learn.saylor.org/", R.drawable.saylor))
        addressList.add(Site("Sci-Hub", "https://sci-hub.se", R.drawable.sci_h))
        addressList.add(Site("Science.gov", "https:science.gov", R.drawable.science_gov))
        addressList.add(Site("Scopus", "https://www.scopus.com/home.uri", R.drawable.scopus))
        addressList.add(Site("Scribd", "https://www.scribd.com", R.drawable.scribd))
        addressList.add(Site("Semantic Scholar", "https://www.semanticscholar.org/", R.drawable.semantic))
        addressList.add(Site("Skillshare", "https://www.skillshare.com/", R.drawable.skillshare))
        addressList.add(Site("Slideshare", "https://slideshare.net", R.drawable.slideshare))
        addressList.add(Site("StackOverflow", "https://stackoverflow.com", R.drawable.stack))
        addressList.add(Site("UCC Library", "https://library.ucc.edu.gh", R.drawable.ucclogo))
        addressList.add(Site("Udacity", "https://www.udacity.com/", R.drawable.udacity))
        addressList.add(Site("Udemy", "https://www.udemy.com", R.drawable.udemy))
        addressList.add(Site("Virtual LRC", "https://www.virtuallrc.com/", R.drawable.virtuallrc))
        addressList.add(Site("Web of Science", "https://clarivate.com/webofsciencegroup/solutions/web-of-science/", R.drawable.web_of_sc))
        addressList.add(Site("Wikipedia", "https://www.wikipedia.org/", R.drawable.wikipedia_icon))
        addressList.add(Site("Wikisource", "https://wikisourse.org/wiki/Main_Page", R.drawable.wikisource))
        addressList.add(Site("Wolfram Alpha", "https://www.wolframalpha.com/", R.drawable.wolfram))
        addressList.add(Site("World Digital Library", "https://www.wdl.org/", R.drawable.wdl))
        addressList.add(Site("WorldWideScience", "https://worldwidescience.org/", R.drawable.worldwidesci))
        addressList.add(Site("YouTube", "https://youtube.com", R.drawable.youtube_icon))

        return addressList
    }
}