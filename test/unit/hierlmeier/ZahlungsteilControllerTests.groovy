package hierlmeier



import org.junit.*
import grails.test.mixin.*


@TestFor(ZahlungsteilController)
@Mock(Zahlungsteil)
class ZahlungsteilControllerTests {


    @Test
    void testIndex() {
        controller.index()
        assert "/zahlungsteil/list" == response.redirectedUrl
    }

    @Test
    void testList() {

        def model = controller.list()

        assert model.zahlungsteilInstanceList.size() == 0
        assert model.zahlungsteilInstanceTotal == 0

    }

    @Test
    void testCreate() {
       def model = controller.create()

       assert model.zahlungsteilInstance != null


    }

    @Test
    void testSave() {
        controller.save()

        assert model.zahlungsteilInstance != null
        assert view == '/zahlungsteil/create'

        // TODO: Populate valid properties

        controller.save()

        assert response.redirectedUrl == '/zahlungsteil/show/1'
        assert controller.flash.message != null
        assert Zahlungsteil.count() == 1
    }


    @Test
    void testShow() {
        controller.show()

        assert flash.message != null
        assert response.redirectedUrl == '/zahlungsteil/list'


        def zahlungsteil = new Zahlungsteil()

        // TODO: populate domain properties

        assert zahlungsteil.save() != null

        params.id = zahlungsteil.id

        def model = controller.show()

        assert model.zahlungsteilInstance == zahlungsteil
    }

    @Test
    void testEdit() {
        controller.edit()

        assert flash.message != null
        assert response.redirectedUrl == '/zahlungsteil/list'


        def zahlungsteil = new Zahlungsteil()

        // TODO: populate valid domain properties

        assert zahlungsteil.save() != null

        params.id = zahlungsteil.id

        def model = controller.edit()

        assert model.zahlungsteilInstance == zahlungsteil
    }

    @Test
    void testUpdate() {

        controller.update()

        assert flash.message != null
        assert response.redirectedUrl == '/zahlungsteil/list'

        response.reset()


        def zahlungsteil = new Zahlungsteil()

        // TODO: populate valid domain properties

        assert zahlungsteil.save() != null

        // test invalid parameters in update
        params.id = zahlungsteil.id

        controller.update()

        assert view == "/zahlungsteil/edit"
        assert model.zahlungsteilInstance != null

        zahlungsteil.clearErrors()

        // TODO: populate valid domain form parameter
        controller.update()

        assert response.redirectedUrl == "/zahlungsteil/show/$zahlungsteil.id"
        assert flash.message != null
    }

    @Test
    void testDelete() {
        controller.delete()

        assert flash.message != null
        assert response.redirectedUrl == '/zahlungsteil/list'

        response.reset()

        def zahlungsteil = new Zahlungsteil()

        // TODO: populate valid domain properties
        assert zahlungsteil.save() != null
        assert Zahlungsteil.count() == 1

        params.id = zahlungsteil.id

        controller.delete()

        assert Zahlungsteil.count() == 0
        assert Zahlungsteil.get(zahlungsteil.id) == null
        assert response.redirectedUrl == '/zahlungsteil/list'


    }


}