package hierlmeier



import org.junit.*
import grails.test.mixin.*


@TestFor(LeistungController)
@Mock(Leistung)
class LeistungControllerTests {


    @Test
    void testIndex() {
        controller.index()
        assert "/leistung/list" == response.redirectedUrl
    }

    @Test
    void testList() {

        def model = controller.list()

        assert model.leistungInstanceList.size() == 0
        assert model.leistungInstanceTotal == 0

    }

    @Test
    void testCreate() {
       def model = controller.create()

       assert model.leistungInstance != null


    }

    @Test
    void testSave() {
        controller.save()

        assert model.leistungInstance != null
        assert view == '/leistung/create'

        // TODO: Populate valid properties

        controller.save()

        assert response.redirectedUrl == '/leistung/show/1'
        assert controller.flash.message != null
        assert Leistung.count() == 1
    }


    @Test
    void testShow() {
        controller.show()

        assert flash.message != null
        assert response.redirectedUrl == '/leistung/list'


        def leistung = new Leistung()

        // TODO: populate domain properties

        assert leistung.save() != null

        params.id = leistung.id

        def model = controller.show()

        assert model.leistungInstance == leistung
    }

    @Test
    void testEdit() {
        controller.edit()

        assert flash.message != null
        assert response.redirectedUrl == '/leistung/list'


        def leistung = new Leistung()

        // TODO: populate valid domain properties

        assert leistung.save() != null

        params.id = leistung.id

        def model = controller.edit()

        assert model.leistungInstance == leistung
    }

    @Test
    void testUpdate() {

        controller.update()

        assert flash.message != null
        assert response.redirectedUrl == '/leistung/list'

        response.reset()


        def leistung = new Leistung()

        // TODO: populate valid domain properties

        assert leistung.save() != null

        // test invalid parameters in update
        params.id = leistung.id

        controller.update()

        assert view == "/leistung/edit"
        assert model.leistungInstance != null

        leistung.clearErrors()

        // TODO: populate valid domain form parameter
        controller.update()

        assert response.redirectedUrl == "/leistung/show/$leistung.id"
        assert flash.message != null
    }

    @Test
    void testDelete() {
        controller.delete()

        assert flash.message != null
        assert response.redirectedUrl == '/leistung/list'

        response.reset()

        def leistung = new Leistung()

        // TODO: populate valid domain properties
        assert leistung.save() != null
        assert Leistung.count() == 1

        params.id = leistung.id

        controller.delete()

        assert Leistung.count() == 0
        assert Leistung.get(leistung.id) == null
        assert response.redirectedUrl == '/leistung/list'


    }


}