package hierlmeier



import org.junit.*
import grails.test.mixin.*


@TestFor(KundeController)
@Mock(Kunde)
class KundeControllerTests {


    @Test
    void testIndex() {
        controller.index()
        assert "/kunde/list" == response.redirectedUrl
    }

    @Test
    void testList() {

        def model = controller.list()

        assert model.kundeInstanceList.size() == 0
        assert model.kundeInstanceTotal == 0

    }

    @Test
    void testCreate() {
       def model = controller.create()

       assert model.kundeInstance != null


    }

    @Test
    void testSave() {
        controller.save()

        assert model.kundeInstance != null
        assert view == '/kunde/create'

        // TODO: Populate valid properties

        controller.save()

        assert response.redirectedUrl == '/kunde/show/1'
        assert controller.flash.message != null
        assert Kunde.count() == 1
    }


    @Test
    void testShow() {
        controller.show()

        assert flash.message != null
        assert response.redirectedUrl == '/kunde/list'


        def kunde = new Kunde()

        // TODO: populate domain properties

        assert kunde.save() != null

        params.id = kunde.id

        def model = controller.show()

        assert model.kundeInstance == kunde
    }

    @Test
    void testEdit() {
        controller.edit()

        assert flash.message != null
        assert response.redirectedUrl == '/kunde/list'


        def kunde = new Kunde()

        // TODO: populate valid domain properties

        assert kunde.save() != null

        params.id = kunde.id

        def model = controller.edit()

        assert model.kundeInstance == kunde
    }

    @Test
    void testUpdate() {

        controller.update()

        assert flash.message != null
        assert response.redirectedUrl == '/kunde/list'

        response.reset()


        def kunde = new Kunde()

        // TODO: populate valid domain properties

        assert kunde.save() != null

        // test invalid parameters in update
        params.id = kunde.id

        controller.update()

        assert view == "/kunde/edit"
        assert model.kundeInstance != null

        kunde.clearErrors()

        // TODO: populate valid domain form parameter
        controller.update()

        assert response.redirectedUrl == "/kunde/show/$kunde.id"
        assert flash.message != null
    }

    @Test
    void testDelete() {
        controller.delete()

        assert flash.message != null
        assert response.redirectedUrl == '/kunde/list'

        response.reset()

        def kunde = new Kunde()

        // TODO: populate valid domain properties
        assert kunde.save() != null
        assert Kunde.count() == 1

        params.id = kunde.id

        controller.delete()

        assert Kunde.count() == 0
        assert Kunde.get(kunde.id) == null
        assert response.redirectedUrl == '/kunde/list'


    }


}